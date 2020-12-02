package com.ifsaid.shark.controller;

import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.QiniuUtils;
import com.ifsaid.shark.util.VerifyCodeUtils;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 文件上传 제어장치
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */
@Slf4j
@Api(tags = "[Qiniu Cloud] 파일 업로드")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    /**
     * 사진 업로드
     *
     * @param file
     * @return java.io.File
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:03
     */
    @ApiOperation(value = "사진 업로드", notes = "일반 이미지 업로드：500px * 500px")
    @PostMapping("/image")
    public JsonResult uploadImages(MultipartFile file) throws IOException {
        if (file.isEmpty() || StringUtils.isEmpty(file.getOriginalFilename())) {
            JsonResult.fail("사진은 비워 둘 수 없습니다.");
        }
        String contentType = file.getContentType();
        if (!contentType.contains("")) {
            JsonResult.fail("사진 형식은 비워 둘 수 없습니다.");
        }
        String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG)$";
        Matcher matcher = Pattern.compile(reg).matcher(file.getOriginalFilename());
        // 校验 图片的后缀名 是否符合要求
        if (matcher.find()) {
            Map<String, String> map = uploadFile(file, 500, 500);
            return JsonResult.success(map);
        }
        return JsonResult.fail("이미지 형식이 잘못되었습니다. [JPG, JPEG, PNG] 중 하나만 업로드 할 수 있습니다.");
    }


    /**
     * 사용자 아바타 업로드
     *
     * @param file
     * @return java.io.File
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:03
     */
    @ApiOperation(value = "아바타 업로드", notes = "일반 이미지 업로드：200px * 200px")
    @PostMapping("/avatar")
    public JsonResult uploadAvatar(MultipartFile file) throws IOException {
        if (file.isEmpty() || StringUtils.isEmpty(file.getOriginalFilename())) {
            JsonResult.fail("아바타는 비워 둘 수 없습니다.");
        }
        String contentType = file.getContentType();
        if (!contentType.contains("")) {
            JsonResult.fail("아바타 형식은 비워 둘 수 없습니다.");
        }
        String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG)$";
        Matcher matcher = Pattern.compile(reg).matcher(file.getOriginalFilename());
        // 그림의 접미사가 요구 사항을 충족하는지 확인
        if (matcher.find()) {
            Map<String, String> map = uploadFile(file, 200, 200);
            return JsonResult.success(map);
        }
        return JsonResult.fail("아바타 형식이 잘못되었습니다. 업로드 만[ JPG , JPEG , PNG ]中的一种");
    }

    /**
     * 삭제 사진
     *
     * @param url
     * @return java.io.File
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:03
     */
    @ApiOperation(value = "사진삭제", notes = "URL 삭제 사진에 따르면")
    @DeleteMapping("/delete")
    public JsonResult deleteImages(String url) throws IOException {
        if (StringUtils.isNotEmpty(url)) {
            String fileKey = url.substring((url.lastIndexOf("/") + 1), url.length());
            log.info("deleteImages url: {}  fileKey: {}", url, fileKey);
            qiniuUtils.delete(fileKey);
        }
        return JsonResult.success();
    }

    /**
     * 이미지 압축 및 업로드 Qiniu Cloud OSS
     *
     * @param file
     * @param height
     * @param width
     * @return Map<String, String>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:06
     */
    private Map<String, String> uploadFile(MultipartFile file, int width, int height) throws IOException {
        // 빈 파일 가져 오기
        File targetFile = qiniuUtils.makeParentFolder(file.getOriginalFilename());
        // 스프링 부트 패키지이기 때문에 jar 패키지이므로 ClassPathResource를 사용하여 클래스 경로 아래에 워터 마크 이미지를 가져와야합니다.
        InputStream watermark = new ClassPathResource("images/watermark.png").getInputStream();
        // 그림이 압축되어 빈 파일로 읽혀집니다.
        Thumbnails.of(file.getInputStream())
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.5f)
                .outputQuality(0.8f)
                .size(width, height).toFile(targetFile);
        // 압축 된 사진을 Qiniu Cloud에 업로드
        String url = qiniuUtils.upload(new FileInputStream(targetFile), targetFile.getName());
        Map<String, String> result = new HashMap<>(2);
        result.put("url", url);
        result.put("fileName", targetFile.getName());
        log.info("uploadFile( {} , {} , {} , {} );", file.getOriginalFilename(), file.getSize(), file.getContentType(), targetFile.getName(), url);
        // 압축 파일 삭제
        targetFile.delete();
        return result;
    }


}
