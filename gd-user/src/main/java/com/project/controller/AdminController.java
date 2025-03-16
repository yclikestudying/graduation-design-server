package com.project.controller;

import com.project.annotation.RoleCheck;
import com.project.common.BaseResponse;
import com.project.constant.RoleConstant;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员模块")
public class AdminController {

}
