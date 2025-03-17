package com.project.controller;

import com.project.common.BaseResponse;
import com.project.dto.friend.AddRequest;
import com.project.dto.friend.CancelRequest;
import com.project.service.FriendService;
import com.project.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/friend")
@Api(tags = "关系模块")
public class FriendController {
    @Resource
    private FriendService friendService;

    /**
     * 添加关注
     * @param addRequest 用户id
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加关注")
    public BaseResponse<String> add(@Validated @RequestBody AddRequest addRequest) {
        boolean result = friendService.add(UserContext.getUserId(), addRequest.getFolloweeId());
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 取消关注
     * @param cancelRequest 用户id
     */
    @PostMapping("/cancel")
    @ApiOperation(value = "取消关注")
    public BaseResponse<String> cancel(@Validated @RequestBody CancelRequest cancelRequest) {
        boolean result = friendService.cancel(UserContext.getUserId(), cancelRequest.getFolloweeId());
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 查询用户是否被关注
     * @param userId 用户id
     */
    @GetMapping("/queryFriend")
    @ApiOperation(value = "查询用户是否被关注")
    public BaseResponse<Boolean> queryFriend(@RequestParam("userId") Long userId) {
        boolean result = friendService.queryFriend(userId);
        return BaseResponse.success(result);
    }

    /**
     * 查询我的关注数量
     */
    @GetMapping("/friendCount")
    @ApiOperation(value = "查询我的关注数量")
    public BaseResponse<Integer> friendCount() {
        Integer count = friendService.friendCount(UserContext.getUserId());
        return BaseResponse.success(count);
    }
}
