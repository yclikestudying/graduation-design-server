package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.AdminService;
import com.project.vo.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 删除单个用户
     */
    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "删除单个用户")
    public BaseResponse<String> deleteUser(@RequestParam("userId") Long userId){
        boolean result = adminService.deleteUser(userId);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 批量删除用户
     */
    @PutMapping("/deleteUserBatch")
    @ApiOperation(value = "批量删除用户")
    public BaseResponse<String> deleteUserBatch(@RequestBody List<Long> userIdList){
        boolean result = adminService.deleteUserBatch(userIdList);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 设置用户为管理员
     */
    @PostMapping("/settingAdmin")
    @ApiOperation(value = "设置用户为管理员")
    public BaseResponse<String> settingAdmin(@RequestBody Map<String, Long> map) {
        boolean result = adminService.settingAdmin(map.get("userId"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 分页查询管理员
     */
    @GetMapping("/queryAdmin")
    @ApiOperation(value = "分页查询管理员")
    public BaseResponse<Map<String, Object>> queryAdmin(@RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        Map<String, Object> map = adminService.queryAdmin(current, size);
        return BaseResponse.success(map);
    }

    /**
     * 模糊查询管理员
     */
    @GetMapping("/queryLikeAdmin")
    @ApiOperation(value = "模糊查询管理员")
    public BaseResponse<Map<String, Object>> queryLikeAdmin(@RequestParam("keyword") String keyword, @RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        Map<String, Object> map = adminService.queryLikeAdmin(keyword, current, size);
        return BaseResponse.success(map);
    }
}
