package com.ifsaid.shark.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 일반 쿼리 매개 변수
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
public class QueryParameter implements java.io.Serializable {

    /**
     * @description: 현재 페이지
     * @date: 2019/12/11 21:02
     */
    private int pageNum = 1;

    /**
     * @description: 페이지 당 데이터 수
     * @date: 2019/12/11 21:02
     */
    private int pageSize = 10;

    /**
     * @description: 키워드 검색
     * @date: 2019/12/11 21:02
     */
    private String keywords;

}
