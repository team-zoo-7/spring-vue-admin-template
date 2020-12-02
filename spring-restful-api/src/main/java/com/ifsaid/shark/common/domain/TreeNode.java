package com.ifsaid.shark.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 *
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 21:31
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeNode implements java.io.Serializable {

    /**
     * 현재 노드 ID
     *
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:32
     */
    private long id;

    /**
     * 현재 노드 제목
     *
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:32
     */
    private String title;

    /**
     * 상위 노드 ID
     *
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:32
     */
    private long parentId;

    /**
     * 소스 속성
     *
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:32
     */
    private Object source;

    /**
     * 모든 자식 노드
     *
     * @JsonInclude(JsonInclude.Include.NON_NULL)
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:32
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeNode> children;

}
