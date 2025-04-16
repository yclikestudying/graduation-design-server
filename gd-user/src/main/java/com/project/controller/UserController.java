package com.project.controller;

import com.project.annotation.RoleCheck;
import com.project.common.BaseResponse;
import com.project.constant.RedisConstant;
import com.project.constant.RoleConstant;
import com.project.domain.User;
import com.project.dto.user.UserLoginRequest;
import com.project.dto.user.UserRegisterRequest;
import com.project.service.UserService;
import com.project.utils.RedisUtil;
import com.project.utils.UserContext;
import com.project.vo.user.QueryUserVO;
import com.project.vo.user.UserVO;
import com.project.vo.visit.QueryVisitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 用户注册
     *
     * @param userLoginRequest 注册信息
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户手机注册")
    public BaseResponse<String> login(@Validated @RequestBody UserRegisterRequest userLoginRequest) {
        boolean result = userService.register(userLoginRequest);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 登录信息
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户手机登录")
    public BaseResponse<Map<String, Object>> login(@Validated @RequestBody UserLoginRequest userLoginRequest) {
        Map<String, Object> map = userService.login(userLoginRequest);
        return BaseResponse.success(map);
    }

    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户退出")
    public BaseResponse<String> logout() {
        // 清除 token
        redisUtil.redisTransaction(RedisConstant.getRedisKey(RedisConstant.USER_TOKEN, UserContext.getUserId()));
        // 清除用户个人信息
        redisUtil.redisTransaction(RedisConstant.getRedisKey(RedisConstant.USER_INFO, UserContext.getUserId()));
        return BaseResponse.success();
    }

    /**
     * 获取用户个人信息
     *
     * @param userId id
     */
    @GetMapping("/getUser")
    @ApiOperation(value = "获取用户个人信息")
    public BaseResponse<UserVO> getUser(@RequestParam(value = "userId", required = false) Long userId) {
        UserVO userVO = userService.getUser(userId);
        return BaseResponse.success(userVO);
    }

    /**
     * 用户注销账号
     */
    @DeleteMapping("/userDeleteAccount")
    @ApiOperation(value = "用户注销账号")
    public BaseResponse<UserVO> userDeleteAccount() {
        boolean result = userService.userDeleteAccount();
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 修改用户个人信息
     *
     * @param map 修改信息
     */
    @PutMapping("/updateUser")
    @ApiOperation(value = "修改用户个人信息")
    public BaseResponse<String> updateUserInfo(@RequestBody Map<String, Object> map) {
        boolean result = userService.updateUser(map);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 用户名模糊查询用户
     *
     * @param keyword 关键字
     */
    @GetMapping("queryUser")
    @ApiOperation(value = "用户名模糊查询用户")
    public BaseResponse<List<QueryUserVO>> queryUser(@RequestParam("keyword") String keyword) {
        List<QueryUserVO> list = userService.queryUser(keyword);
        return BaseResponse.success(list);
    }

    /**
     * 查询用户头像
     *
     * @param userId 用户id
     */
    @GetMapping("/getAvatar")
    @ApiOperation(value = "查询用户头像")
    public BaseResponse<String> getAvatar(@RequestParam(value = "userId", required = false) Long userId) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String avatar = userService.getAvatar(userId);
        return BaseResponse.success(avatar);
    }

    /**
     * 上传头像
     *
     * @param userId 用户id
     * @param file   文件数据
     */
    @PostMapping("/uploadAvatar")
    @ApiOperation(value = "上传头像")
    public BaseResponse<String> uploadAvatar(@RequestParam("userId") Long userId,
                                             @RequestParam("file") MultipartFile file) {
        String avatar = userService.uploadAvatar(userId, file);
        return BaseResponse.success(avatar);
    }

    /**
     * 查询关注用户
     *
     * @param userId 用户id
     */
    @GetMapping("/queryFriend")
    @ApiOperation(value = "查询关注用户")
    public BaseResponse<List<QueryUserVO>> queryFriend(@RequestParam(value = "userId", required = false) Long userId) {
        List<QueryUserVO> list = userService.queryFriend(userId);
        return BaseResponse.success(list);
    }

    /**
     * 查询粉丝用户
     *
     * @param userId 用户id
     */
    @GetMapping("/queryFans")
    @ApiOperation(value = "查询粉丝用户")
    public BaseResponse<List<QueryUserVO>> queryFans(@RequestParam(value = "userId", required = false) Long userId) {
        List<QueryUserVO> list = userService.queryFans(userId);
        return BaseResponse.success(list);
    }

    /**
     * 查询互关用户
     *
     * @param userId 用户id
     */
    @GetMapping("/queryEach")
    @ApiOperation(value = "查询互关用户")
    public BaseResponse<List<QueryUserVO>> queryEach(@RequestParam(value = "userId", required = false) Long userId) {
        List<QueryUserVO> list = userService.queryEach(userId);
        return BaseResponse.success(list);
    }

    /**
     * 更改手机
     */
    @PutMapping ("/editPhone")
    @ApiOperation(value = "更改手机")
    public BaseResponse<String> editPhone(@RequestBody Map<String, String> map) {
        boolean result = userService.editPhone(map.get("oldPhone"), map.get("newPhone"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 修改密码
     */
    @PutMapping("/editPassword")
    @ApiOperation(value = "修改密码")
    public BaseResponse<String> editPassword(@RequestBody Map<String, String> map) {
        boolean result = userService.editPassword(map.get("oldPassword"), map.get("newPassword"), map.get("checkPassword"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
