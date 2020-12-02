package com.ifsaid.shark.entity;

import com.ifsaid.shark.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 백엔드 시스템 사용자
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Entity
@Table(name = "tb_sys_user")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity implements java.io.Serializable {

    /**
     * 사용자 고유 ID
     *
     * @date: 2019/12/11 22:15
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 암호
     *
     * @date: 2019/12/11 22:15
     */
    private String password;

    /**
     * 성별
     *
     * @date: 2019/12/11 22:15
     */
    private Integer gender;

    /**
     * 생일
     *
     * @date: 2019/12/11 22:15
     */
    private LocalDate birthday;

    /**
     * 부서 Id
     *
     * @date: 2019/12/11 22:15
     */
    private Integer deptId;

    /**
     * 상태 [ 0.비활성화 1.정상 2.삭제 ]
     *
     * @date: 2019/12/11 22:15
     */
    private Integer status;

}