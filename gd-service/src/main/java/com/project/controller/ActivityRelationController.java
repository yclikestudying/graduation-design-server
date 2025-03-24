package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.ActivityRelationService;
import com.project.vo.activityRelation.ActivityRelationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/activityRelation")
@Api(tags = "活动关系模块")
public class ActivityRelationController {
    @Resource
    private ActivityRelationService activityRelationService;

    /**
     * 加入群聊
     */
    @PostMapping("/addActivity")
    @ApiOperation(value = "加入群聊")
    public BaseResponse<String> addActivity(@RequestBody Map<String, Long> map) {
        boolean result = activityRelationService.addActivity(map.get("activityId"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
