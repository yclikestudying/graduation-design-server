package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.ArticleService;
import com.project.vo.article.QueryArticleVO;
import com.project.vo.user.QueryUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@Api(tags = "动态模块")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    /**
     * 发布动态
     */
    @PostMapping("/upload")
    @ApiOperation(value = "发布动态")
    public BaseResponse<String> upload(@RequestParam(value = "file0", required = false) MultipartFile file0,
                                      @RequestParam(value = "file1", required = false) MultipartFile file1,
                                      @RequestParam(value = "file2", required = false) MultipartFile file2,
                                      @RequestParam(value = "file3", required = false) MultipartFile file3,
                                      @RequestParam(value = "file4", required = false) MultipartFile file4,
                                      @RequestParam(value = "file5", required = false) MultipartFile file5,
                                      @RequestParam(value = "file6", required = false) MultipartFile file6,
                                      @RequestParam(value = "file7", required = false) MultipartFile file7,
                                      @RequestParam(value = "file8", required = false) MultipartFile file8,
                                      @RequestParam(value = "text", required = false) String text) {

        Map<Integer, Object> map = new HashMap<>();
        if (file0 != null) {
            map.put(0, file0);
        }
        if (file1 != null) {
            map.put(1, file1);
        }
        if (file2 != null) {
            map.put(2, file2);
        }
        if (file3 != null) {
            map.put(3, file3);
        }
        if (file4 != null) {
            map.put(4, file4);
        }
        if (file5 != null) {
            map.put(5, file5);
        }
        if (file6 != null) {
            map.put(6, file6);
        }
        if (file7 != null) {
            map.put(7, file7);
        }
        if (file8 != null) {
            map.put(8, file8);
        }
        boolean result = articleService.upload(map, text);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 查询用户动态
     * @param userId 用户id
     */
    @GetMapping("/queryArticle")
    @ApiOperation(value = "查询动态")
    public BaseResponse<List<QueryArticleVO>> queryArticle(@RequestParam(value = "userId", required = false) Long userId) {
        List<QueryArticleVO> list = articleService.queryArticle(userId);
        return BaseResponse.success(list);
    }

    /**
     * 根据id查询动态信息
     * @param articleId 动态id
     */
    @GetMapping("/queryOne")
    @ApiOperation(value = "根据id查询动态信息")
    public BaseResponse<QueryArticleVO> queryOne(@RequestParam("articleId") Long articleId) {
        QueryArticleVO one = articleService.queryOne(articleId);
        return BaseResponse.success(one);
    }

    /**
     * 根据id删除动态
     * @param articleId 动态id
     */
    @DeleteMapping("/deleteArticle")
    @ApiOperation(value = "根据id删除动态")
    public BaseResponse<String> deleteArticle(@RequestParam("articleId") Long articleId) {
        boolean result = articleService.deleteArticle(articleId);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 查询校园动态
     */
    @GetMapping("/queryArticleOfSchool")
    @ApiOperation(value = "查询校园动态")
    public BaseResponse<List<QueryArticleVO>> queryArticleOfSchool() {
        List<QueryArticleVO> list = articleService.queryArticleOfSchool();
        return BaseResponse.success(list);
    }

    /**
     * 查询关注动态
     */
    @GetMapping("/queryArticleOfAttention")
    @ApiOperation(value = "查询关注动态")
    public BaseResponse<List<QueryArticleVO>> queryArticleOfAttention() {
        List<QueryArticleVO> list = articleService.queryArticleOfAttention();
        return BaseResponse.success(list);
    }

    /**
     * 查询我的动态数量
     */
    @GetMapping("/articleCount")
    @ApiOperation(value = "查询我的动态数量")
    public BaseResponse<Integer> articleCount() {
        Integer count = articleService.articleCount();
        return BaseResponse.success(count);
    }

    /**
     * 关键字模糊查询动态
     */
    @GetMapping("/queryArticleByKeyword")
    @ApiOperation(value = "关键字模糊查询动态")
    public BaseResponse<List<QueryArticleVO>> queryArticleByKeyword(@RequestParam("keyword") String keyword) {
        List<QueryArticleVO> list = articleService.queryArticleByKeyword(keyword);
        return BaseResponse.success(list);
    }
}
