package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.ExpressService;
import com.project.vo.article.QueryArticleVO;
import com.project.vo.express.QueryExpressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/express")
@Api(tags = "跑腿模块")
public class ExpressController {
    @Resource
    private ExpressService expressService;

    /**
     * 发布跑腿任务
     */
    @PostMapping("/upload")
    @ApiOperation(value = "发布跑腿任务")
    public BaseResponse<String> upload(@RequestBody Map<String, String> map) {
        boolean result = expressService.upload(map.get("text"));
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 查询自己的跑腿任务
     */
    @PostMapping("/queryExpress")
    @ApiOperation(value = "查询自己的跑腿任务")
    public BaseResponse<List<QueryExpressVO>> queryExpress(@RequestParam(value = "userId", required = false) Long userId) {
        List<QueryExpressVO> list = expressService.queryExpress(userId);
        return BaseResponse.success(list);
    }

    /**
     * 查询所有跑腿任务
     */
    @PostMapping("/queryAllExpress")
    @ApiOperation(value = "查询所有跑腿任务")
    public BaseResponse<List<QueryExpressVO>> queryAllExpress() {
        List<QueryExpressVO> list = expressService.queryAllExpress();
        return BaseResponse.success(list);
    }

    /**
     * 根据id删除跑腿任务
     */
    @DeleteMapping("/deleteExpress")
    @ApiOperation(value = "根据id删除跑腿任务")
    public BaseResponse<String> deleteExpress(@RequestParam("expressId") Long expressId) {
        boolean result = expressService.deleteExpress(expressId);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 关键字模糊查询跑腿任务
     */
    @GetMapping("/queryExpressByKeyword")
    @ApiOperation(value = "关键字模糊查询跑腿任务")
    public BaseResponse<List<QueryExpressVO>> queryExpressByKeyword(@RequestParam("keyword") String keyword) {
        List<QueryExpressVO> list = expressService.queryExpressByKeyword(keyword);
        return BaseResponse.success(list);
    }
}
