package com.ifsaid.shark.service;

import com.ifsaid.shark.common.service.BaseService;
import com.ifsaid.shark.entity.SysRole;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 역할 서비스 인터페이스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


public interface SysRoleService extends BaseService<SysRole, Integer> {

    /**
     * 수정역할의권한
     *
     * @param rid
     * @param permissionIds
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:21
     */
    int updateRolePermissions(Integer rid, Set<Integer> permissionIds);

    /**
     * 사용자 ID가 소유 한 역할 찾기
     *
     * @param uid
     * @return Set<SysRole>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:21
     */
    Set<SysRole> findAllByUserId(Integer uid);

}
