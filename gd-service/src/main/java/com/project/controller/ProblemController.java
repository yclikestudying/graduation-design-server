package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.ProblemService;
import com.project.vo.problem.QueryProblemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/problem")
@Api(tags = "问题反馈表")
public class ProblemController {
    @Resource
    private ProblemService problemService;

    /**
     * 提交问题和意见
     */
    @PostMapping("/upload")
    @ApiOperation(value = "提交问题和意见")
    public BaseResponse<String> upload(@RequestParam("file") MultipartFile file,
                                       @RequestParam("content") String content,
                                       @RequestParam(value = "phone",required = false) String phone){
        boolean result = problemService.uplaod(file, content, phone);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 查询问题和意见
     */
    @GetMapping("/queryProblem")
    @ApiOperation(value = "查询问题和意见")
    public BaseResponse<List<QueryProblemVO>> queryProblem() {
        List<QueryProblemVO> list = problemService.queryProblem();
        return BaseResponse.success(list);
    }

    /**
     * 清空全部反馈记录
     */
    @DeleteMapping("/deleteAll")
    @ApiOperation(value = "清空全部反馈记录")
    public BaseResponse<String> deleteAll() {
        boolean result = problemService.deleteAll();
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
