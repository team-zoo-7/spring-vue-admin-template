package com.ifsaid.shark.common.service;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.domain.BaseEntity;
import com.ifsaid.shark.util.QueryParameter;

import java.util.List;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 일반 서비스 인터페이스
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface BaseService<T extends BaseEntity, ID> {

    /**
     * 페이지 수 쿼리
     *
     * @param parameter
     * @return PageInfo<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    PageInfo<T> findAllPage(QueryParameter parameter);

    /**
     * 모든 데이터보기, 조건에 따라 쿼리
     *
     * @param entity
     * @return List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    List<T> findAll(T entity);

    /**
     * ID 기반 쿼리 데이터
     *
     * @param id
     * @return T
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    T findById(ID id);

    /**
     * 조건부 쿼리에 따라 하나의 데이터 만 반환됩니다.
     *
     * @param entity
     * @return T
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    T find(T entity);

    /**
     * 추가 데이터
     *
     * @param entity
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int create(T entity);

    /**
     * 일괄 추가 데이터, 추가 수량 반환
     *
     * @param list
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int batchCreate(List<T> list);

    /**
     * 수정 데이터, ID가 있어야하며 수정 수를 반환합니다.
     *
     * @param entity
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int update(T entity);

    /**
     * ID 삭제 데이터에 따라 삭제 된 개수를 반환
     *
     * @param id
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int deleteById(ID id);

    /**
     * 삭제 조건에 따라 삭제 횟수를 반환합니다.
     *
     * @param entity
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int delete(T entity);

    /**
     * ID에 따라 데이터 존재 여부 확인
     *
     * @param entity
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int count(T entity);

    /**
     * ID에 따라 데이터 존재 여부 확인
     *
     * @param id
     * @return boolean
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    boolean existsWithPrimaryKey(ID id);

}
