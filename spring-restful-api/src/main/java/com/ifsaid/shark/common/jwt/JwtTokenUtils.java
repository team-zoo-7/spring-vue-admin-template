package com.ifsaid.shark.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * jwt 도구
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenUtils implements Serializable {

    private static final String CLAIM_KEY_USER_ACCOUNT = "sub";

    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * @description: 비밀 키
     * @date: 2019/12/11 21:53
     */
    private String secret;

    /**
     * @description: 만료 시각
     * @date: 2019/12/11 21:53
     */
    private Long expiration;

    private String tokenHeader;

    private String tokenHead;

    /**
     * 데이터 클레임에서 토큰 생성
     *
     * @param claims 데이터 선언
     * @return 토큰
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 토큰에서 데이터 클레임 가져 오기
     *
     * @param token
     * @return 데이터 선언
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 토큰 생성
     *
     * @param userDetails 사용자
     * @return 토큰
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 토큰에서 사용자 이름 가져 오기
     *
     * @param token
     * @return 사용자이름
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 토큰이 만료되었는지 확인
     *
     * @param token
     * @return 만료 됨
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰 새로 고침
     *
     * @param token 원래 토큰
     * @return 새 토큰
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 토큰 가로채기
     *
     * @param completeToken Bearer로 시작하는 전체 토큰을 가로 챕니다
     * @return 새 토큰
     */
    public String interceptCompleteToken(String completeToken) {
        String authToken = completeToken.substring(this.getTokenHead().length());
        return authToken;
    }


    /**
     * 확인 토큰
     *
     * @param token
     * @param userDetails 사용자
     * @return 유효여부
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}
