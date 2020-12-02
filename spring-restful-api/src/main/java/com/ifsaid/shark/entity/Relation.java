package com.ifsaid.shark.entity;

import lombok.*;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 권한 설정과 관련하여
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
@AllArgsConstructor
@NoArgsConstructor
public class Relation implements java.io.Serializable {

    /**
     * @description 예 : 사용자가 역할과 연관된 경우 테이블에 사용자 ID가 표시됩니다.
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 20:36
     */
    private Integer targetId;

    /**
     * @description 예 : 사용자가 역할과 연관된 경우 테이블에 역할 ID가 표시됩니다.
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 20:36
     */
    private Integer sourceId;

}
