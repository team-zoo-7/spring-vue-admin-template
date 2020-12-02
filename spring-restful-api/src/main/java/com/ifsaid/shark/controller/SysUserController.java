package com.ifsaid.shark.controller;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.controller.BaseController;
import com.ifsaid.shark.entity.SysUser;
import com.ifsaid.shark.service.SysUserService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.QueryParameter;
import com.ifsaid.shark.vo.SysUserVo;
import com.ifsaid.shark.vo.UserRelatedRoleVo;
import com.ifsaid.shark.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 사용자 제어장치
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 권한관리 ] 사용자")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUser, Integer, SysUserService> {
    @Autowired
    protected SysUserService sysUserService;

    @ApiOperation(value = "페이지수,사용자 세부 정보 얻기", notes = "페이지수 전체조회，사용자 세부 정보 얻기")
    @GetMapping("/info/page")
    public JsonResult findAllInfoPage(QueryParameter parameter) {
        PageInfo<SysUserVo> page = baseService.findAllPageInfo(parameter);
        return JsonResult.success(page.getTotal(), page.getList());
    }

    @ApiOperation(value = "사용자 세부 정보 얻기", notes = "사용자 세부 정보 얻기")
    @GetMapping("/info")
    public JsonResult<UserVo> findUserInfo() {
        return JsonResult.success(baseService.findUserInfo());
    }

    @ApiOperation(value = "사용자 역할 수정", notes = "사용자 역할 수정,이전 역할 정보를 삭제합니다.")
    @PostMapping("/update/roles")
    public JsonResult updateRolePermissions(@RequestBody @Validated UserRelatedRoleVo data, BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        int result = baseService.updateUserRoles(data.getUid(), data.getRoles());
        return JsonResult.success(result);
    }


}
