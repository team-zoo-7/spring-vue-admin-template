package com.ifsaid.shark.service.impl;

import com.ifsaid.shark.common.service.impl.BaseServiceImpl;
import com.ifsaid.shark.entity.Relation;
import com.ifsaid.shark.entity.SysRole;
import com.ifsaid.shark.mapper.SysRoleMapper;
import com.ifsaid.shark.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


@Slf4j
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, Integer, SysRoleMapper> implements SysRoleService {

    @Override
    public int updateRolePermissions(Integer rid, Set<Integer> permissionIds) {
        List<Relation> collect = permissionIds.stream()
                // 중복 된 권한 ID 제거
                .distinct()
                .filter(pid -> pid != null && pid > 0)
                // Relation 개체 생성
                .map(res -> new Relation(rid, res))
                .collect(Collectors.toList());
        log.info("collect: {}", collect);
        // 첫 번째 삭제 현재 역할, 모든 소유 권한
        baseMapper.deleteHavePermissions(rid);
        // 새 권한 할당
        return baseMapper.insertByPermissions(collect);
    }

    @Override
    public Set<SysRole> findAllByUserId(Integer uid) {
        return baseMapper.findAllByUserId(uid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(Integer rid) {
        if (baseMapper.userReference(rid) > 0) {
            throw new RuntimeException("현재 역할, 다른 사용자 참조가 있으며 삭제할 수 없습니다.！");
        }
        // 현재 역할, 자신의 권한
        baseMapper.deleteHavePermissions(rid);
        // 삭제역할
        return baseMapper.deleteByPrimaryKey(rid);
    }

}
