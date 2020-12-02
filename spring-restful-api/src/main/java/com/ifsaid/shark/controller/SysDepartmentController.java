package com.ifsaid.shark.controller;

import com.ifsaid.shark.common.controller.BaseController;
import com.ifsaid.shark.common.domain.TreeNode;
import com.ifsaid.shark.entity.SysDepartment;
import com.ifsaid.shark.service.SysDepartmentService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.TreeNodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 부서 제어장치
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 권한관리 ] 부서")
@RestController
@RequestMapping("/sys/department")
public class SysDepartmentController extends BaseController<SysDepartment, Integer, SysDepartmentService> {

    @ApiOperation(value = "전체조회[트리 노드]", notes = "트리 노드 형태로 표시")
    @GetMapping("/tree")
    public JsonResult tree() {
        List<TreeNode> collect = baseService.findAll(null)
                .stream()
                .distinct()
                .map(res -> new TreeNode(res.getId(), res.getName(), res.getParentId(), res, null))
                .collect(Collectors.toList());
        List<TreeNode> roots = TreeNodeUtils.findRoots(collect);
        return JsonResult.success(roots);
    }

}
