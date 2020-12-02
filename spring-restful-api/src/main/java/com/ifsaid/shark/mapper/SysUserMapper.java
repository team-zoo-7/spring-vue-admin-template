package com.ifsaid.shark.mapper;

import com.ifsaid.shark.common.mapper.BaseMapper;
import com.ifsaid.shark.entity.Relation;
import com.ifsaid.shark.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 사용자 매퍼 인터페이스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysUserMapper extends BaseMapper<SysUser, Integer> {

    /**
     * 사용자 이름과 별명에 따르면 퍼지 매치
     *
     * @param keywords
     * @return Set<SysRole>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:04
     */
    Set<SysUser> selectByKeywords(String keywords);

    /**
     * 역할 관련, 다중 역할
     *
     * @param record
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:25
     */
    int insertByRoles(List<Relation> record);

    /**
     * 특정 사용자, 역할 소유
     *
     * @param uid
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:25
     */
    int deleteHaveRoles(Integer uid);

}
