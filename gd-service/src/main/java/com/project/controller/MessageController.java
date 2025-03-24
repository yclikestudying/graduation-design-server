package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.MessageService;
import com.project.vo.message.QueryGroupMessageVO;
import com.project.vo.message.QueryMessageVO;
import com.project.vo.message.QueryNoReadMessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 查询最新消息列表
     */
    @GetMapping("/queryNoReadList")
    @ApiOperation(value = "查询未读消息列表")
    public BaseResponse<List<QueryNoReadMessageVO>> queryNoReadListAndTotal() {
        List<QueryNoReadMessageVO> list = messageService.queryNoReadList();
        return BaseResponse.success(list);
    }

    /**
     * 查询未读消息总数
     */
    @GetMapping("/queryNoReadTotal")
    @ApiOperation(value = "查询未读消息总数")
    public BaseResponse<Integer> queryNoReadTotal() {
        return BaseResponse.success(messageService.queryNoReadTotal());
    }

    /**
     * 上传图片
     */
    @PostMapping("/uploadImage")
    @ApiOperation(value = "上传图片")
    public BaseResponse<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return BaseResponse.success(messageService.uploadImage(file));
    }

    /**
     * 查询群聊消息
     */
    @GetMapping("/queryGroupMessage/{activityId}")
    @ApiOperation(value = "查询群聊消息")
    public BaseResponse<List<QueryGroupMessageVO>> queryGroupMessage(@PathVariable("activityId") Long activityId) {
        List<QueryGroupMessageVO> list = messageService.queryGroupMessage(activityId);
        return BaseResponse.success(list);
    }
}
