package com.ifsaid.shark.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * jackson Json 도구
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
public class JsonUtils {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @description: 객체를 json 문자열로 변환。
     * @param: [jsonData, beanType]
     * @return: java.util.List<T>
     * @author: Wang Chen Chen <932560435@qq.com>
     * @date: 2019/4/18 15:26
     */
    public static String objectToJson(Object data) {
        try {
            String result = MAPPER.writeValueAsString(data);
            return result;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * @description: json 결과 집합을 객체로 변환
     * @param: [jsonData, beanType]
     * @return: java.util.List<T>
     * @author: Wang Chen Chen <932560435@qq.com>
     * @date: 2019/4/18 15:26
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * @description: json 결과 집합을 객체로 변환
     * @param: [jsonData, beanType]
     * @return: java.util.List<T>
     * @author: Wang Chen Chen <932560435@qq.com>
     * @date: 2019/4/18 15:26
     */
    public static <T> T jsonToPojo(byte[] src, Class<T> beanType) {
        try {
            return MAPPER.readValue(src, beanType);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * @description: json 데이터를 pojo 객체 목록으로 변환
     * @param: [jsonData, beanType]
     * @return: java.util.List<T>
     * @author: Wang Chen Chen <932560435@qq.com>
     * @date: 2019/4/18 15:26
     */
    public static <T> List<T> jsonToListPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, beanType));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * @description: JSON 데이터를 Map으로 변환
     * @param: [jsonData, keyType, valueType]
     * @return: java.util.List<T>
     * @author: Wang Chen Chen <932560435@qq.com>
     * @date: 2019/4/18 15:26
     */
    public static <K, V> Map<K, V> jsonToMap(String jsonData, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructMapType(HashMap.class, keyType, valueType));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * @description: json 데이터를 pojo 객체 목록으로 변환
     * @param: [jsonData, beanType]
     * @return: java.util.List<T>
     * @author: Wang Chen Chen <932560435@qq.com>
     * @date: 2019/4/18 15:26
     */
    public static <K, V> List<Map<K, V>> jsonToListMap(String jsonData, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(
                    jsonData,
                    MAPPER.getTypeFactory().constructCollectionType(List.class,
                            MAPPER.getTypeFactory().constructMapType(HashMap.class, keyType, valueType)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


}
