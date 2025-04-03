package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.LikesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/likes")
@Api(tags = "点赞模块")
public class LikesController {
    @Resource
    private LikesService likesService;

    /**
     * 点赞
     */
    @PostMapping("/like")
    @ApiOperation(value = "点赞")
    public BaseResponse<String> like(@RequestBody Map<String, Long> map) {
        boolean result = likesService.like(map.get("articleId"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 取消点赞
     */
    @PostMapping("/cancelLike")
    @ApiOperation(value = "取消点赞")
    public BaseResponse<String> cancelLike(@RequestBody Map<String, Long> map) {
        boolean result = likesService.cancelLike(map.get("articleId"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
