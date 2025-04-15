package com.project.controller;

import com.project.service.ProblemService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/problem")
@Api(tags = "问题反馈表")
public class ProblemController {
    @Resource
    private ProblemService problemService;
}
