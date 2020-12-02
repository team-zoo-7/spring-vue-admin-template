package com.ifsaid.shark.common.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifsaid.shark.constant.StatusConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 스프링 보안 인증 UserDetails 구현 클래스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser implements UserDetails {

    /**
     * 사용자 고유 ID
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 20:48
     */
    private Integer uid;

    /**
     * 사용자 로그인시 사용한 사용자 이름
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 20:48
     */
    private String username;

    /**
     * 사용자 로그인시 암호 사용
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 20:48
     */
    private String password;

    /**
     * 사용자상태， [ 0.비활성화 1.정상 2.삭제 ]
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 20:48
     */
    private Integer status;

    private Collection<? extends GrantedAuthority> authorities;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return status == StatusConstant.NORMAL;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
