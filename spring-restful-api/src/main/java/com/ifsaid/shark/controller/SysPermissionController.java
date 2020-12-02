package com.ifsaid.shark.controller;

import com.ifsaid.shark.common.controller.BaseController;
import com.ifsaid.shark.common.domain.TreeNode;
import com.ifsaid.shark.constant.PermissionType;
import com.ifsaid.shark.entity.SysPermission;
import com.ifsaid.shark.service.SysPermissionService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.TreeNodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 권한 제어장치
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 권한관리 ] 권한")
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController extends BaseController<SysPermission, Integer, SysPermissionService> {

    @ApiOperation(value = "전체조회[트리 노드]", notes = "트리 노드 형태로 표시 <br> \n\n 필터가 참이면 필터링됩니다. 버튼. 거짓 인 경우. 메뉴와 버튼은")
    @GetMapping("/tree")
    public JsonResult tree(@RequestParam(defaultValue = "false") boolean filter) {
        List<TreeNode> collect = baseService.findAll(null)
                .stream()
                .distinct()
                // 필터가 참이면 필터링됩니다. 버튼. 거짓 인 경우. 메뉴와 버튼은
                .filter(res -> filter ? PermissionType.MENU.equals(res.getType().toLowerCase()) : true)
                .map(res -> new TreeNode(res.getPid(), res.getTitle(), res.getParentId(), res, new ArrayList<>()))
                .collect(Collectors.toList());
        return JsonResult.success(TreeNodeUtils.findRoots(collect));
    }


}
