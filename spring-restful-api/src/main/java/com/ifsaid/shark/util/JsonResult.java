package com.ifsaid.shark.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * RESTful API는 항목 클래스를 균일하게 반환합니다.
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 1456468469856161L;

    /**
     * 반환 상태 코드
     */
    private Integer status;

    /**
     * 반환 메시지
     */
    private String message;

    /**
     * 반환 내용
     */
    private T data;

    private JsonResult() {
    }

    /**
     * 맞춤 반품 상태 코드
     *
     * @param status
     * @param message
     * @param data
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    private static <T> JsonResult result(int status, String message, T data) {
        JsonResult result = new JsonResult();
        result.status = status;
        result.message = message;
        result.data = data;
        return result;
    }

    /**
     * 맞춤 반품 상태 코드
     *
     * @param httpStatus
     * @param data
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    private static <T> JsonResult result(HttpStatus httpStatus, T data) {
        return result(httpStatus.status, httpStatus.value, data);
    }

    /**
     * 맞춤 반환 상태 코드 [권장되지 않음, 확장 방법 권장]
     *
     * @param status
     * @param message
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult result(int status, String message) {
        return result(status, message, null);
    }


    /**
     * 작업 성공
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success() {
        return success(HttpStatus.OK.value, null);
    }

    /**
     * 작업 성공
     *
     * @param total
     * @param currentPage
     * @param list
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(long total, T list) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("total", total);
        map.put("list", list);
        return success(HttpStatus.OK.value, map);
    }

    /**
     * 작업 성공
     *
     * @param message
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(String message) {
        return success(message, null);
    }

    /**
     * 작업 성공
     *
     * @param data
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(T data) {
        return success(HttpStatus.OK.value, data);
    }

    /**
     * 작업 성공
     *
     * @param message
     * @param data
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(String message, T data) {
        return result(HttpStatus.OK.status, message, data);
    }


    /**
     * 작업 실패
     *
     * @param message
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult fail(String message) {
        return result(HttpStatus.FAIL.status, message, null);
    }


    /**
     * 무단
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult error401() {
        return result(HttpStatus.UNAUTHORIZED.status, HttpStatus.UNAUTHORIZED.value, null);
    }


    /**
     * 지원되지 않는 요청 유형
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult error415() {
        return result(HttpStatus.UNSUPPORTED_MEDIA_TYPE.status, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value, null);
    }


    /**
     * 404 찾을 수 없음
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult error404() {
        return result(HttpStatus.NOT_FOUND.status, HttpStatus.NOT_FOUND.value, null);
    }


    /**
     * 서버 내부 오류
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult error500() {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.status, HttpStatus.INTERNAL_SERVER_ERROR.value, null);
    }


    /**
     * restful api 상태 코드를 반환합니다. 충분하지 않을 때 자신을 확장하십시오
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @return com.ifsaid.shark.util.JsonResult
     * @date 2019/12/12 21:08
     */
    @Getter
    public enum HttpStatus {

        /**
         * 정상 반환 코드
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        OK(200, "Ok"),

        /**
         * 오류 반환 코드
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        FAIL(0, "Fail"),

        /**
         * 404 찾다
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        NOT_FOUND(404, "Not Found"),

        /**
         * 무단
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        UNAUTHORIZED(401, "권한이 없습니다."),

        /**
         * 지원되지 않는 미디어 유형
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        UNSUPPORTED_MEDIA_TYPE(415, "이 요청 유형은 지원되지 않습니다.！"),


        /**
         * 서버 내부 오류 반환 코드
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        INTERNAL_SERVER_ERROR(500, "서버 내부 오류");

        private int status;

        private String value;

        HttpStatus(int status, String value) {
            this.status = status;
            this.value = value;
        }

    }

}