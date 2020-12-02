package com.ifsaid.shark.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * [ 통용 ]중국행정구역테이블
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/25 22:01
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Entity
@Table(name = "tb_cn_area")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChinaArea implements java.io.Serializable {

    /**
     * @description: 관리 코드 [ 고유 ]
     * @date: 2019/12/11 22:15
     */
    @Id
    private Long areaCode;

    /**
     * @description: 수평
     * @date: 2019/12/11 22:15
     */
    private Integer level;

    /**
     * @description: 부모관리 코드
     * @date: 2019/12/11 22:15
     */
    private Long parentCode;

    /**
     * @description: 우편 번호
     * @date: 2019/12/11 22:15
     */
    private Integer zipCode;

    /**
     * @description: 지역코드
     * @date: 2019/12/11 22:15
     */
    private String cityCode;

    /**
     * @description: 이름
     * @date: 2019/12/11 22:15
     */
    private String name;

    /**
     * @description: 약어
     * @date: 2019/12/11 22:15
     */
    private String shortName;

    /**
     * @description: 조합 이름
     * @date: 2019/12/11 22:15
     */
    private String mergerName;

    /**
     * @description: 병음
     * @date: 2019/12/11 22:15
     */
    private String pinyin;

    /**
     * @description: 경도
     * @date: 2019/12/11 22:15
     */
    private Double lng;

    /**
     * @description: 위도
     * @date: 2019/12/11 22:15
     */
    private Double lat;

}
