package com.ifsaid.shark.service.impl;

import com.ifsaid.shark.common.service.impl.BaseServiceImpl;
import com.ifsaid.shark.entity.SysPermission;
import com.ifsaid.shark.mapper.SysPermissionMapper;
import com.ifsaid.shark.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 권한 Service 구현 클래스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, Integer, SysPermissionMapper> implements SysPermissionService {

    @Override
    public Set<SysPermission> findAllByUserId(Integer uid) {
        return baseMapper.findAllByUserId(uid);
    }

    @Override
    public Set<SysPermission> findPermissionByRoleId(Integer rid) {
        return baseMapper.findAllByRoleId(rid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(Integer id) {
        if (baseMapper.haveChildren(id) > 0) {
            throw new RuntimeException("현재 권한 아래에 여전히 하위 권한이 있으며 삭제할 수 없습니다.！");
        }
        if (baseMapper.roleReference(id) > 0) {
            throw new RuntimeException("현재 권한, 인용 된 다른 역할이 있지만 삭제할 수 없습니다.！");
        }
        return baseMapper.deleteByPrimaryKey(id);
    }

}
