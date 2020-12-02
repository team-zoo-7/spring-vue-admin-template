package com.ifsaid.shark.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 토큰 반환 값
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 23:29
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@Builder
public class TokenValue implements java.io.Serializable {

    /**
     * @description: 요청 헤더 값
     */
    private String header;

    /**
     * @description: 토큰 가치
     */
    private String value;

    /**
     * @description: 토큰 접두사
     */
    private String prefix;

    /**
     * @description: 만료 된 시각 (밀리 초, 기본적으로 20 분)
     */
    private Long expiration;

}
