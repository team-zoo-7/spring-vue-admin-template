package com.ifsaid.shark.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 로그인 성공 후 사용자 상세 Vo 모델
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 23:29
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements java.io.Serializable {

    private Integer uid;

    /**
     * 아바타
     *
     * @date: 2019/12/11 22:15
     */
    private String avatar;

    /**
     * 사용자이름
     *
     * @date: 2019/12/11 22:15
     */
    private String username;

    /**
     * 이메일
     *
     * @date: 2019/12/11 22:15
     */
    private String email;

    /**
     * 별명
     *
     * @date: 2019/12/11 22:15
     */
    private String nickname;

    /**
     * 성별
     *
     * @date: 2019/12/11 22:15
     */
    private Integer gender;

    /**
     * 부서이름
     *
     * @date: 2019/12/11 22:15
     */
    private String departmentName;

    /**
     * 역할이름，열 테이블
     *
     * @date: 2019/12/11 22:15
     */
    private Set<String> roles;

    /**
     * 생일
     *
     * @date: 2019/12/11 22:15
     */
    private LocalDate birthday;

    /**
     * @describe 버튼
     * @date 2018/10/29
     * @author Wang Chen Chen
     */
    private List<ButtonVo> buttons;

    /**
     * @describe 메뉴
     * @date 2018/10/29
     * @author Wang Chen Chen
     */
    private List<MenuVo> menus;

}
