package com.ifsaid.shark.service;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.entity.ChinaArea;
import com.ifsaid.shark.util.QueryParameter;

import java.util.List;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 중국 행정 구역 서비스 인터페이스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface ChinaAreaService {

    /**
     * 페이지 수 쿼리
     *
     * @param parameter
     * @return PageInfo<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    PageInfo<ChinaArea> findAllPage(QueryParameter parameter);


    /**
     * 모든 데이터보기, 조건에 따라 쿼리
     *
     * @param entity
     * @return List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    List<ChinaArea> findAll(ChinaArea entity);

    /**
     * ID 기반 쿼리 데이터
     *
     * @param id
     * @return T
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    ChinaArea findById(Long id);

}
