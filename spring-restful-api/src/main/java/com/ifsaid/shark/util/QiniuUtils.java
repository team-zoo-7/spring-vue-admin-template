package com.ifsaid.shark.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * Qiniu Cloud, 파일 업로드 도구
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
@Getter
@Setter
@Component
public class QiniuUtils {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucketName}")
    private String bucketName;

    @Value("${qiniu.fileDomain}")
    private String fileDomain;

    private UploadManager uploadManager;

    private BucketManager bucketManager;

    private Configuration c;

    private Client client;

    private Auth auth;

    public Client getClient() {
        if (client == null) {
            client = new Client(getConfiguration());
        }
        return client;
    }

    public BucketManager getBucketManager() {
        if (bucketManager == null) {
            bucketManager = new BucketManager(getAuth(), getConfiguration());
        }
        return bucketManager;
    }

    public UploadManager getUploadManager() {
        if (uploadManager == null) {
            uploadManager = new UploadManager(getConfiguration());
        }
        return uploadManager;
    }

    public Configuration getConfiguration() {
        if (c == null) {
            c = new Configuration(Region.autoRegion());
        }
        return c;
    }

    public Auth getAuth() {
        if (auth == null) {
            auth = Auth.create(getAccessKey(), getSecretKey());
        }
        return auth;
    }

    /**
     * 간단한 업로드 모드 자격 증명
     *
     * @date 2019/12/11 20:53
     */
    public String getUpToken() {
        return getAuth().uploadToken(getBucketName());
    }

    /**
     * 업로드 모드의 자격 증명 재정의
     *
     * @date 2019/12/11 20:53
     */
    public String getUpToken(String fileKey) {
        return getAuth().uploadToken(getBucketName(), fileKey);
    }

    /**
     * 로컬 파일 업로드
     *
     * @param filePath 로컬 파일 경로
     * @param fileKey  Qiniuhou에 업로드 된 파일 경로
     * @return String
     * @throws IOException
     */
    public String upload(String filePath, String fileKey) throws IOException {
        Response res;
        try {
            res = getUploadManager().put(filePath, fileKey, getUpToken(fileKey));
            DefaultPutRet putRet = JsonUtils.jsonToPojo(res.body(), DefaultPutRet.class);
            return fileDomain + "/" + putRet.key;
        } catch (QiniuException e) {
            res = e.response;
            log.error(e.getMessage());
            return "上传失败";
        }
    }

    /**
     * 바이너리 데이터를 업로드하고 파일의 UIR을 반환합니다.
     *
     * @param data
     * @param fileKey
     * @return String
     * @throws IOException
     */
    public String upload(byte[] data, String fileKey) throws IOException {
        Response res;
        try {
            res = getUploadManager().put(data, fileKey, getUpToken(fileKey));
            DefaultPutRet putRet = JsonUtils.jsonToPojo(res.body(), DefaultPutRet.class);
            return fileDomain + "/" + putRet.key;
        } catch (QiniuException e) {
            res = e.response;
            e.printStackTrace();
            return "업로드 실패";
        }
    }

    /**
     * 입력 스트림을 업로드하고 파일의 UIR을 반환합니다.
     *
     * @param inputStream
     * @param fileKey
     * @return String
     * @throws IOException
     */
    public String upload(InputStream inputStream, String fileKey) throws IOException {
        Response res;
        try {
            res = getUploadManager().put(inputStream, fileKey, getUpToken(fileKey), null, null);
            DefaultPutRet putRet = JsonUtils.jsonToPojo(res.body(), DefaultPutRet.class);
            return fileDomain + "/" + putRet.key;
        } catch (QiniuException e) {
            res = e.response;
            e.printStackTrace();
            return "업로드 실패";
        }
    }

    /**
     * 삭제 파일
     *
     * @param fileKey
     * @return boolean
     * @throws QiniuException
     */
    public boolean delete(String fileKey) throws QiniuException {
        Response response = getBucketManager().delete(this.getBucketName(), fileKey);
        return response.statusCode == 200 ? true : false;
    }

    /**
     * 공용 공간 파일 가져 오기
     *
     * @param fileKey
     * @return String
     * @throws Exception
     */
    public String getFile(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
        String url = String.format("%s/%s", fileDomain, encodedFileName);
        return url;
    }

    /**
     * 개인 공간 파일 가져 오기
     *
     * @param fileKey
     * @return String
     * @throws Exception
     */
    public String getPrivateFile(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
        String url = String.format("%s/%s", fileDomain, encodedFileName);
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;
        //1小时，可以自定义链接过期시각
        return auth.privateDownloadUrl(url, expireInSeconds);
    }


    /**
     *  / 년 / 월 / 일 /시 / 분 / 초 /로 나눈 폴더 생성
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @return String
     * @date 2019/12/13 23:09
     */
    public String createFolder() {
        LocalDateTime dateTime = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append(dateTime.getYear())
                .append("/")
                .append(dateTime.getMonthValue())
                .append("/")
                .append(dateTime.getDayOfMonth())
                .append("/")
                .append(dateTime.getHour())
                .append("/")
                .append(dateTime.getMinute())
                .append("/");
        return sb.toString();
    }

    /**
     *  원본 파일 이름에 따라 uuid 파일 이름 생성
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @return java.lang.String
     * @date 2019/12/13 23:10
     */
    public String createFileName(String originalFileName) {
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return UUID.randomUUID().toString().toLowerCase().replace("-", "") + "." + suffix;
    }


    /**
     *  폴더 생성
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @param fileName
     * @return java.io.File
     * @date 2019/12/13 23:06
     */
    public File makeParentFolder(String fileName) {
        // 파일의 원래 이름을 가져오고 UUID 파일 이름을 생성합니다.
        String uuidFileName = createFileName(fileName);
        // 빌드 파일 경로
        StringBuilder sb = new StringBuilder(System.getProperties().getProperty("user.home"));
        sb.append("/imagesCache/").append(uuidFileName);
        log.info("캐시 파일 주소 생성:  {}", sb.toString());
        // 파일 생성
        File originalFile = new File(sb.toString());
        // 파일의 상위 디렉토리가 있는지 확인
        if (!originalFile.getParentFile().exists()) {
            // 파일 디렉토리 생성
            originalFile.getParentFile().mkdir();
        }
        return originalFile;
    }

    /**
     *  파일 이름 / 년 / 월 / 일 /시 / 분 / 초 / uud 파일 이름에서 생성
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @return java.lang.String
     * @exception
     * @date 2019/12/13 23:10
     */
    public String createFolderFileName(String originalFileName) {
        return createFolder() + createFileName(originalFileName);
    }

}
