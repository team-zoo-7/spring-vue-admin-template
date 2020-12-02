package com.ifsaid.shark.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 사용자 관련 역할
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/16 21:14
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
public class RoleRelatedPermissionVo implements java.io.Serializable {

    @NotNull(message = "역할 ID는 비워 둘 수 없습니다.!")
    private Integer rid;

    @NotNull(message = "권한 ID 열 테이블, 비워 둘 수 없습니다!")
    @Size(min = 1, message = "역할 권한 최소 하나의 값 할당")
    private Set<Integer> permissions;

}
