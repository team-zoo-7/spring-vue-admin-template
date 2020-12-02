package com.ifsaid.shark.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 사용자 관련 역할
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/16 21:15
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
public class UserRelatedRoleVo implements java.io.Serializable {

    @NotNull(message = "사용자 ID는 비워 둘 수 없습니다!")
    private Integer uid;

    @NotNull(message = "역할 ID 열 테이블, 비워 둘 수 없습니다!")
    @Size(min = 1, message = "사용자 역할 적어도 하나의 값을 할당")
    private Set<Integer> roles;

}
