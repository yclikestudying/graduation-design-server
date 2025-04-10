package com.project.controller;

import com.project.annotation.RoleCheck;
import com.project.common.BaseResponse;
import com.project.constant.RoleConstant;
import com.project.service.AdminService;
import com.project.vo.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员模块")
public class AdminController {
    @Resource
    private AdminService adminService;

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "管理员登录")
    public BaseResponse<Map<String, Object>> login(@RequestBody Map<String, String> map) {
        Map<String, Object> stringObjectsMap = adminService.login(map.get("userPhone"), map.get("userPassword"));
        return BaseResponse.success(stringObjectsMap);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public BaseResponse<String> logout() {
        return BaseResponse.success();
    }
}
