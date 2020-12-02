package com.ifsaid.shark.vo;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 사용자 로그인，매개 변수 전달
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements java.io.Serializable {

    @NotNull(message = "사용자 이름은 비워 둘 수 없습니다.")
    @Length(min = 5, message = "사용자 이름의 길이는 5 자리 미만일 수 없습니다.")
    private String username;

    @NotNull(message = "암호는 비워 둘 수 없습니다")
    @Length(min = 5, message = "암호의 길이는 5 자리 이상이어야합니다.")
    private String password;

    @NotNull(message = "인증 코드를 입력해야합니다.")
    @Length(min = 4, max = 4, message = "인증 코드의 길이는 4 자리입니다.")
    private String codeText;

    @NotNull(message = "인증 코드 KEY는 비워 둘 수 없습니다.")
    private String codeKey;

}
