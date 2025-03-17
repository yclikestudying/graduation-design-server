package com.project.controller;

import com.project.service.ExpressService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/express")
@Api(tags = "跑腿模块")
public class ExpressController {
    @Resource
    private ExpressService expressService;
}
