package com.ifsaid.shark.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 상위 항목 클래스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Getter
@Setter
public class BaseEntity implements java.io.Serializable {

    /**
     * @description: 등록일시
     * @date: 2019/12/11 21:12
     */
    private LocalDateTime createTime;

    /**
     * @description: 수정일시
     * @date: 2019/12/11 21:12
     */
    private LocalDateTime lastUpdateTime;

}
