package com.project.controller;

import com.project.service.MessageService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/message")
@Api(tags = "通信模块")
public class MessageController {
    @Resource
    private MessageService messageService;
}
