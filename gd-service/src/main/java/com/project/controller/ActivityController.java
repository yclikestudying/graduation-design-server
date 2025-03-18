package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/activity")
@Api(tags = "活动模块")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    /**
     * 创建活动
     */
    @PostMapping("/upload")
    @ApiOperation(value = "创建活动")
    public BaseResponse<String> upload(@RequestParam("file") MultipartFile file,
                                       @RequestParam("title") String title,
                                       @RequestParam("description") String description,
                                       @RequestParam("max") Integer max){
        boolean result = activityService.uplaod(file, title, description, max);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
