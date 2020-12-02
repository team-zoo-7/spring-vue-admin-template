package com.ifsaid.shark.controller;

import com.ifsaid.shark.common.controller.BaseController;
import com.ifsaid.shark.common.domain.TreeNode;
import com.ifsaid.shark.entity.SysPermission;
import com.ifsaid.shark.entity.SysRole;
import com.ifsaid.shark.service.SysPermissionService;
import com.ifsaid.shark.service.SysRoleService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.TreeNodeUtils;
import com.ifsaid.shark.vo.RoleRelatedPermissionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 역할 제어장치
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 권한관리 ] 역할")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController<SysRole, Integer, SysRoleService> {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value = "권한 열을 포함한 역할의 쿼리 세부 사항 테이블", notes = "ID 별 쿼리")
    @GetMapping("/info/{rid}")
    public JsonResult roleInfo(@PathVariable Integer rid) {
        Map<String, Object> map = new HashMap<>(2);
        // 현재 역할 및 권한 소유 가져 오기
        Set<SysPermission> havePermissionCollect = sysPermissionService.findPermissionByRoleId(rid);

        // 모든 권한 얻기
        List<TreeNode> allPermissionCollect = sysPermissionService.findAll(null)
                .stream()
                .distinct()
                .map(res -> new TreeNode(res.getPid(), res.getTitle(), res.getParentId(), null, null))
                .collect(Collectors.toList());

        // 현재 역할이 소유하고있는 권한 pid를 선택합니다.
        Set<Integer> hashSet = havePermissionCollect.stream().map(res -> res.getPid()).collect(Collectors.toSet());
        // 역할이 소유 한 현재 권한을 다시 탐색 한 다음 부모 노드를 제거하고 자식 노드 만 남겨 둡니다.
        havePermissionCollect.forEach(res -> hashSet.remove(res.getParentId()));
        // 모든 권한을 트리 노드 형태로 재귀
        map.put("all", TreeNodeUtils.findRoots(allPermissionCollect));
        // 현재 역할이 소유하고있는 권한 ID를 열 테이블로 반환
        map.put("have", hashSet);
        return JsonResult.success(map);
    }


    @ApiOperation(value = "수정역할권한", notes = "수정역할권한,삭제 전 권한 정보.")
    @PostMapping("/update/permissions")
    public JsonResult updateRolePermissions(@RequestBody @Validated RoleRelatedPermissionVo data, BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        int result = baseService.updateRolePermissions(data.getRid(), data.getPermissions());
        return JsonResult.success(result);
    }


}
