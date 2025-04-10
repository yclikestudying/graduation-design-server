package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.AdminService;
import com.project.vo.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminClient")
@Api(tags = "管理员模块")
public class AdminController {
    @Resource
    private AdminService adminService;

    /**
     * 分页查询普通用户
     */
    @GetMapping("/queryUser")
    @ApiOperation(value = "分页查询普通用户")
    public BaseResponse<Map<String, Object>> queryUser(@RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        Map<String, Object> map = adminService.queryUser(current, size);
        return BaseResponse.success(map);
    }

    /**
     * 模糊查询普通用户
     */
    @GetMapping("/queryLikeUser")
    @ApiOperation(value = "模糊查询普通用户")
    public BaseResponse<Map<String, Object>> queryLikeUser(@RequestParam("keyword") String keyword, @RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        Map<String, Object> map = adminService.queryLikeUser(keyword, current, size);
        return BaseResponse.success(map);
    }
}
