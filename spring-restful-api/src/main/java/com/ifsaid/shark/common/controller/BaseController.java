package com.ifsaid.shark.common.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.domain.BaseEntity;
import com.ifsaid.shark.common.service.BaseService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.QueryParameter;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 일반 추가, 삭제, 수정, 모두 확인, ID 쿼리 , 페이지수 제어장치
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
public class BaseController<T extends BaseEntity, ID, S extends BaseService<T, ID>> {

    @Autowired
    protected S baseService;

    @ApiOperation(value = "단일 쿼리", notes = "ID 별 쿼리")
    @GetMapping("/{id}")
    public JsonResult<T> findById(@PathVariable("id") ID id) {
        log.info("get ID : {}", id);
        return JsonResult.success(baseService.findById(id));
    }

    @ApiOperation(value = "전체조회", notes = "전체조회")
    @GetMapping("/all")
    public JsonResult<List<T>> findAll() {
        return JsonResult.success(baseService.findAll(null));
    }

    @ApiOperation(value = "페이지수", notes = "페이지수 전체조회")
    @GetMapping("/page")
    public JsonResult<Page<T>> findAll(QueryParameter parameter) {
        PageInfo<T> page = baseService.findAllPage(parameter);
        return JsonResult.success(page.getTotal(), page.getList());
    }

    @ApiOperation(value = "추가", notes = "추가 ID 필요 없음")
    @PostMapping()
    public JsonResult<T> create(@RequestBody T entity) {
        log.info("create:  {}", entity);
        try {
            int result = baseService.create(entity);
            return JsonResult.success(result);
        } catch (Exception e) {
            return JsonResult.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "수정", notes = "수정에는 ID가 있어야합니다.")
    @PutMapping()
    public JsonResult<T> update(@RequestBody T entity) {
        log.info("update:  {}", entity);
        try {
            int result = baseService.update(entity);
            return JsonResult.success(result);
        } catch (Exception e) {
            return JsonResult.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "삭제", notes = "아이디 만 필요")
    @DeleteMapping("/{id}")
    public JsonResult<String> delete(@PathVariable("id") ID id) {
        log.info("delete:  {}", id);
        try {
            int result = baseService.deleteById(id);
            return JsonResult.success(result);
        } catch (Exception e) {
            return JsonResult.fail(e.getMessage());
        }
    }

}
