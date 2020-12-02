package com.ifsaid.shark.service;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.jwt.JwtUser;
import com.ifsaid.shark.common.service.BaseService;
import com.ifsaid.shark.entity.SysUser;
import com.ifsaid.shark.util.QueryParameter;
import com.ifsaid.shark.vo.SysUserVo;
import com.ifsaid.shark.vo.UserVo;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 사용자 서비스 인터페이스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysUserService extends BaseService<SysUser, Integer> {

    /**
     * 수정사용자의역할
     *
     * @param uid
     * @param roleIds
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:20
     */
    int updateUserRoles(Integer uid, Set<Integer> roleIds);

    /**
     * 사용자 로그인
     *
     * @param username 사용자이름
     * @param password 암호
     * @return String token 值
     * @throws AuthenticationException
     */
    String login(String username, String password) throws AuthenticationException;

    /**
     * 사용자 quit 로그인
     */
    void logout(JwtUser loginUser);

    /**
     * 로그인 사용자의 사용자 이름이 올바른지 확인하세요.
     *
     * @param username 사용자이름
     * @return 가동 결과
     * @throws AuthenticationException
     */
    JwtUser validateUsername(String username);


    /**
     * 사용자 세부 정보 얻기
     *
     * @return UserVo
     */
    UserVo findUserInfo();


    /**
     * 페이지 수 사용자 세부 정보 얻기
     *
     * @param parameter
     * @return PageInfo<SysUserVo>
     */
    PageInfo<SysUserVo> findAllPageInfo(QueryParameter parameter);

}
