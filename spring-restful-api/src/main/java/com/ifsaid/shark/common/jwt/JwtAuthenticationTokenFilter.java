package com.ifsaid.shark.common.jwt;

import com.ifsaid.shark.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * jwt 코어 인터셉터
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * 로그인 사용자의 현재 정보 얻기
     * JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     *
     * @throws ServletException
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/19 0:11
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Request에서 요청 헤더를 다음으로 가져옵니다. “ Authorization ” token 값
        String completeToken = request.getHeader(this.jwtTokenUtils.getTokenHeader());
        // 값이 "Bearer"로 시작하는지 확인하십시오.
        log.debug("completeToken:request.getHeader(this.jwtTokenUtils.getTokenHeader())={} ", request.getHeader(this.jwtTokenUtils.getTokenHeader()));
        log.debug("this.jwtTokenUtils.getTokenHead()={} ", this.jwtTokenUtils.getTokenHead());
        if (completeToken != null && completeToken.startsWith(this.jwtTokenUtils.getTokenHead())) {
            // 토큰에서 "Bearer"뒤의 값을 가로 채십시오.
            final String tokenValue = jwtTokenUtils.interceptCompleteToken(completeToken);
            // 토큰 값에 따라 사용자의 사용자 이름을 가져옵니다.
            String username = jwtTokenUtils.getUsernameFromToken(tokenValue);
            log.debug("JwtAuthenticationTokenFilter[doFilterInternal] checking authentication {} ", username);
            // 사용자이름이 유효한지 확인
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 사용자 이름에 따라 redis로 이동하여 사용자 데이터를 쿼리합니다. 토큰이 충분히 신뢰할 수있는 경우이 단계를 생략 할 수 있습니다.
                JwtUser userDetails = sysUserService.validateUsername(username);
                // 토큰과 로그인 사용자가 동일한 지 다시 확인하십시오.
                if (jwtTokenUtils.validateToken(tokenValue, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 사용자 정보를 SecurityContext로 설정하면 다음 문을 사용하여 어디서나 얻을 수 있습니다.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }


}
