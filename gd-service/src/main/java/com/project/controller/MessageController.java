package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.MessageService;
import com.project.vo.message.QueryMessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
@Api(tags = "通信模块")
public class MessageController {
    @Resource
    private MessageService messageService;

    /**
     * 查询聊天信息
     */
    @GetMapping("/queryMessage")
    @ApiOperation(value = "查询聊天信息")
    public BaseResponse<List<QueryMessageVO>> queryMessage(@RequestParam("userId") Long userId) {
        List<QueryMessageVO> list = messageService.queryMessage(userId);
        return BaseResponse.success(list);
    }

    /**
     * 消息已读
     */
    @PostMapping("/isRead")
    @ApiOperation(value = "消息已读")
    public BaseResponse<Boolean> isRead(@RequestBody Map<String, Long> map) {
        return BaseResponse.success(messageService.isRead(map.get("userId")));
    }
}
