package com.ifsaid.shark.controller;

import com.ifsaid.shark.common.jwt.JwtTokenUtils;
import com.ifsaid.shark.common.jwt.JwtUser;
import com.ifsaid.shark.service.SysUserService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.VerifyCodeUtils;
import com.ifsaid.shark.vo.LoginUser;
import com.ifsaid.shark.vo.TokenValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 사용자 인증 제어 장치
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 권한관리 ] 사용자 인증")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    @Lazy
    private RedisTemplate<String, String> redisTemplate;


    @ApiOperation(value = "사용자 로그인 인증", notes = "암호 로그인 형식의 사용자 이름 {\"username\":\"admin\",\"password\":\"admin\"}")
    @PostMapping("/login")
    public JsonResult<TokenValue> login(@RequestBody @Validated LoginUser user, BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        // CodeKey에 따라 redis에서 codeText 가져 오기
        String codeText = redisTemplate.opsForValue().get(user.getCodeKey());
        // redis의 codeText와 사용자가 입력 한 codeText 비교
        // redis.codeText와 user.codeText를 모두 소문자로 변환 한 다음 비교
        if (StringUtils.isEmpty(codeText) || !codeText.toLowerCase().equals(user.getCodeText().toLowerCase())) {
            return JsonResult.fail("확인코드 오류！");
        }
        log.info("LoginUser : {}", user);
        try {
            String jwtToken = sysUserService.login(user.getUsername(), user.getPassword());
            TokenValue tokenValue = TokenValue.builder()
                    .header(jwtTokenUtils.getTokenHeader())
                    .value(jwtToken)
                    .prefix(jwtTokenUtils.getTokenHead())
                    .expiration(jwtTokenUtils.getExpiration())
                    .build();
            // 로그인 성공 후!
            redisTemplate.delete(user.getCodeKey());
            return JsonResult.success("로그인 성공", tokenValue);
        } catch (AuthenticationException ex) {
            return JsonResult.fail(ex.getMessage());
        }
    }


    @ApiOperation(value = "사용자 quit 로그인", notes = "사용자 quit 로그인")
    @GetMapping("/logout")
    public JsonResult<TokenValue> logout() {
        JwtUser loginUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUser != null && !StringUtils.isEmpty(loginUser.getUsername())) {
            sysUserService.logout(loginUser);
            return JsonResult.success();
        }
        return JsonResult.success();
    }


    @ApiOperation(value = "토큰 값 새로 고침", notes = "요청 헤더에 토큰을 첨부하기 만하면됩니다.")
    @GetMapping("/refresh")
    public JsonResult<TokenValue> refresh(@RequestHeader(value = "${jwt.tokenHeader}") String completeToken) {
        // 전체 토큰에서 토큰 값을 자릅니다.
        String oldToken = jwtTokenUtils.interceptCompleteToken(completeToken);
        // 토큰 값에 따라 로그인의 사용자 이름을 가져옵니다.
        String username = jwtTokenUtils.getUsernameFromToken(oldToken);
        if (!StringUtils.isEmpty(username)) {
            // 사용자가 데이터에 존재하는지 확인
            JwtUser details = sysUserService.validateUsername(username);
            if (details != null && !StringUtils.isEmpty(details.getUsername())) {
                // 토큰 값 새로 고침
                String jwtToken = jwtTokenUtils.refreshToken(oldToken);
                // 새 토큰 값 캡슐화
                TokenValue tokenValue = TokenValue.builder()
                        .header(jwtTokenUtils.getTokenHeader())
                        .value(jwtToken)
                        .prefix(jwtTokenUtils.getTokenHead())
                        .expiration(jwtTokenUtils.getExpiration())
                        .build();
                return JsonResult.success("새로 고침 토큰 성공！", tokenValue);
            }
        }
        return JsonResult.fail("토큰 형식 오류!");
    }


    @ApiOperation(value = "그래픽 인증 코드 받기", notes = "그래픽 확인 코드를 가져옵니다. codeKey 프런트 엔드는 무작위로 생성 된 문자열을 전달합니다.")
    @GetMapping("/verify/code/{codeKey}")
    public void imageVerifyCode(@PathVariable String codeKey, HttpServletResponse response) throws IOException {
        // 응답 유형 형식을 그림 형식으로 설정
        response.setContentType("image/jpeg");
        // 이미지 캐싱 비활성화。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 사진 인증 코드 받기
        VerifyCodeUtils.ImageVerifyCode image = VerifyCodeUtils.getImage();
        // 사진 인증 코드의 텍스트 받기
        String codeText = image.getCodeText();

        // 인증 코드의 codeKey 및 codeText 입력, redis에 저장, 유효한 시각은 10 분
        redisTemplate.opsForValue().set(codeKey, codeText, 10, TimeUnit.MINUTES);

        ImageIO.write(image.getImage(), "JPEG", response.getOutputStream());
    }

}
