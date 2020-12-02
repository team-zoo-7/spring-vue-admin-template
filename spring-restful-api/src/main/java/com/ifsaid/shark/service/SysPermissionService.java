package com.ifsaid.shark.service;

import com.ifsaid.shark.common.service.BaseService;
import com.ifsaid.shark.entity.SysPermission;

import java.util.Set;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 권한 서비스 인터페이스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysPermissionService extends BaseService<SysPermission, Integer> {

    /**
     * 사용자 ID에 따라 권한 소유를 쿼리합니다.
     *
     * @param uid
     * @return Set<SysPermission>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:23
     */
    Set<SysPermission> findAllByUserId(Integer uid);

    /**
     * 역할 Id에 따르면 쿼리에 권한이 있습니다.
     *
     * @param rid
     * @return Set<Integer>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:22
     */
    Set<SysPermission> findPermissionByRoleId(Integer rid);

}
