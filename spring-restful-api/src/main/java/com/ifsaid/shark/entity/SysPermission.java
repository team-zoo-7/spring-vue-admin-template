package com.ifsaid.shark.entity;

import com.ifsaid.shark.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 백엔드 시스템 권한
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Entity
@Table(name = "tb_sys_permission")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysPermission extends BaseEntity implements java.io.Serializable {

    /**
     * @description: 권한 고유 ID
     * @date: 2019/12/11 22:15
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    /**
     * @description: 권한 상급권한ID
     * @date: 2019/12/11 22:15
     */
    private Integer parentId;

    /**
     * @description: 권한 전역 리소스 식별자
     * @date: 2019/12/11 22:15
     */
    private String resources;

    /**
     * @description: 권한 표제
     * @date: 2019/12/11 22:15
     */
    private String title;

    /**
     * @description: 권한，메뉴 인 경우 아이콘 이름입니다. 버튼 인 경우 값을 할당 할 필요가 없습니다.
     * @date: 2019/12/11 22:15
     */
    private String icon;

    /**
     * @description: 권한，버튼 또는 메뉴는 하나만 선택할 수 있습니다.
     * @date: 2019/12/11 22:15
     */
    private String type;

    /**
     * @description: 권한 기술
     * @date: 2019/12/11 22:15
     */
    private String description;

}