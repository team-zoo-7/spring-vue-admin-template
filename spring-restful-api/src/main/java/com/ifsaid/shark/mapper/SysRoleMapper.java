package com.ifsaid.shark.mapper;

import com.ifsaid.shark.common.mapper.BaseMapper;
import com.ifsaid.shark.entity.Relation;
import com.ifsaid.shark.entity.SysRole;

import java.util.List;
import java.util.Set;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 역할 매퍼 인터페이스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysRoleMapper extends BaseMapper<SysRole, Integer> {

    /**
     * 사용자 ID를 기반으로 소유 된 역할 열 테이블 쿼리
     *
     * @param uid
     * @return Set<SysRole>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:04
     */
    Set<SysRole> findAllByUserId(Integer uid);

    /**
     * 역할 연관, 다중 권한
     *
     * @param record
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:25
     */
    int insertByPermissions(List<Relation> record);

    /**
     * 특정 역할, 모든 권한 소유
     *
     * @param rid
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:25
     */
    int deleteHavePermissions(Integer rid);

    /**
     * 특정 역할이 여전히 다른 사용자에 의해 인용되는지 확인
     *
     * @param rid
     * @return Integer
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/22 19:50
     */
    Integer userReference(Integer rid);

    /**
     * 역할 이름 및 기술에 따르면 퍼지 일치
     *
     * @param keywords
     * @return Set<SysRole>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:04
     */
    Set<SysRole> selectByKeywords(String keywords);

}
