package com.project.controller;

import com.project.common.BaseResponse;
import com.project.dto.friend.AddRequest;
import com.project.service.FriendService;
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
        boolean result = friendService.add(addRequest.getFollowerId(), addRequest.getFolloweeId());
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
