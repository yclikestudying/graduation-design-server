package com.project.controller;

import com.project.common.BaseResponse;
import com.project.domain.PrivateSetting;
import com.project.service.PrivateSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/privateSetting")
@Api(tags = "系统隐私模块")
public class PrivateSettingController {
    @Resource
    private PrivateSettingService privateSettingService;

    /**
     * 查询当前用户系统隐私设置
     */
    @GetMapping("/querySetting")
    @ApiOperation(value = "查询当前用户系统隐私设置")
    public BaseResponse<PrivateSetting> querySetting(@RequestParam(value = "userId", required = false) Long userId) {
        PrivateSetting privateSetting = privateSettingService.querySetting(userId);
        return BaseResponse.success(privateSetting);
    }

    /**
     * 设置隐私
     */
    @PutMapping("/setting")
    @ApiOperation(value = "设置隐私")
    public BaseResponse<String> setting(@RequestBody Map<String, Object> map) {
        boolean result = privateSettingService.setting(map.get("key"), map.get("value"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
