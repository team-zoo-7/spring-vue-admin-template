package com.ifsaid.shark.mapper;

import com.ifsaid.shark.common.mapper.BaseMapper;
import com.ifsaid.shark.entity.SysPermission;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 권한 매퍼 인터페이스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysPermissionMapper extends BaseMapper<SysPermission, Integer> {

    /**
     * 사용자 ID를 기반으로 소유 권한 가져 오기
     *
     * @param uid
     * @return Set<SysPermission>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:43
     */
    Set<SysPermission> findAllByUserId(Integer uid);

    /**
     * 역할 ID를 기반으로 소유 권한 가져 오기
     *
     * @param rid
     * @return Set<SysPermission>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:43
     */
    Set<SysPermission> findAllByRoleId(Integer rid);

    /**
     * 특정 권한이 다운되었는지 여부와 여전히 권한이 있는지 확인
     *
     * @param pid
     * @return Integer
     * @throws
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/22 19:50
     */
    Integer haveChildren(Integer pid);


    /**
     * 권한이 여전히 다른 역할에 의해 인용되는지 확인
     *
     * @param pid
     * @return Integer
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/22 19:50
     */
    Integer roleReference(Integer pid);

}
