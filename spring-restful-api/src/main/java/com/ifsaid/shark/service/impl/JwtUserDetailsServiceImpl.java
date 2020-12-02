package com.ifsaid.shark.service.impl;

import com.ifsaid.shark.common.jwt.JwtUser;
import com.ifsaid.shark.entity.SysUser;
import com.ifsaid.shark.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * spring security 인증 UserDetailsService 구현 클래스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.find(SysUser.builder().username(username).build());
        if (user == null || StringUtils.isEmpty(user.getUid())) {
            throw new UsernameNotFoundException(String.format("'%s'.이 사용자는 존재하지 않습니다", username));
        } else {
            return new JwtUser(user.getUid(), user.getUsername(), user.getPassword(), user.getStatus(), null);
        }
    }


}
