package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.VisitorService;
import com.project.vo.visit.QueryVisitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visitor")
@Api(tags = "访客模块")
public class VisitorController {
    @Resource
    private VisitorService visitorService;

    /**
     * 添加访客记录
     */
    @PostMapping("/addVisitor")
    @ApiOperation(value = "添加访客记录")
    public BaseResponse<String> addVisitor(@RequestBody Map<String, Long> map) {
        boolean result = visitorService.addVisitor(map.get("visitorId"), map.get("visitedId"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }


    /**
     * 查询访客记录
     */
    @GetMapping("/queryVisitor")
    @ApiOperation(value = "查询访客记录")
    public BaseResponse<List<QueryVisitVO>> queryVisitor() {
        List<QueryVisitVO> list = visitorService.queryVisitor();
        return BaseResponse.success(list);
    }

    /**
     * 删除当前用户全部访客记录
     */
    @DeleteMapping("/deleteVisitor")
    @ApiOperation(value = "删除当前用户全部访客记录")
    public BaseResponse<String> deleteVisitor() {
        boolean result = visitorService.deleteVisitor();
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
