package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.ExpressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/express")
@Api(tags = "跑腿模块")
public class ExpressController {
    @Resource
    private ExpressService expressService;

    /**
     * 发布跑腿任务
     */
    @PostMapping("/upload")
    @ApiOperation(value = "发布跑腿任务")
    public BaseResponse<String> upload(@RequestBody Map<String, String> map) {
        boolean result = expressService.upload(map.get("text"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
