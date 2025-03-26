package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.GroupMessageService;
import com.project.vo.message.QueryGroupMessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/groupMessage")
@Api(tags = "群消息模块")
public class GroupMessageController {
    @Resource
    private GroupMessageService groupMessageService;

    /**
     * 查询群聊消息
     */
    @GetMapping("/queryGroupMessage/{activityId}")
    @ApiOperation(value = "查询群聊消息")
    public BaseResponse<List<QueryGroupMessageVO>> queryGroupMessage(@PathVariable("activityId") Long activityId) {
        List<QueryGroupMessageVO> list = groupMessageService.queryGroupMessage(activityId);
        return BaseResponse.success(list);
    }
}
