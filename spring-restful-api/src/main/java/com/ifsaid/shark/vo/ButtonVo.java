package com.ifsaid.shark.vo;

import lombok.*;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 사용자 로그인 성공 후 버튼 권한으로 돌아 가기
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
public class ButtonVo implements java.io.Serializable {

    private Integer pid;

    private String resources;

    private String title;


}
