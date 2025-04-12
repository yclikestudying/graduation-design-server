package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.GoodsService;
import com.project.vo.article.QueryArticleVO;
import com.project.vo.goods.QueryGoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
@Api(tags = "商品模块")
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    /**
     * 上传商品
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传商品")
    public BaseResponse<String> upload(@RequestParam("file") MultipartFile file,
                                       @RequestParam("title") String title,
                                       @RequestParam("text") String text,
                                       @RequestParam("oldPrice") String oldPrice,
                                       @RequestParam("price") String price) {
        boolean result = goodsService.upload(file, title, text, oldPrice, price);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 查询个人商品
     */
    @GetMapping("/queryGoods")
    @ApiOperation(value = "查询个人商品")
    public BaseResponse<List<QueryGoodsVO>> queryGoods(@RequestParam(value = "userId", required = false) Long userId) {
        List<QueryGoodsVO> list = goodsService.queryGoods(userId);
        return BaseResponse.success(list);
    }

    /**
     * 查询全部商品
     */
    @GetMapping("/queryAllGoods")
    @ApiOperation(value = "查询全部商品")
    public BaseResponse<List<QueryGoodsVO>> queryAllGoods() {
        List<QueryGoodsVO> list = goodsService.queryAllGoods();
        return BaseResponse.success(list);
    }

    /**
     * 关键字模糊查询商品
     */
    @GetMapping("/queryGoodsByKeyword")
    @ApiOperation(value = "关键字模糊查询商品")
    public BaseResponse<List<QueryGoodsVO>> queryArticleByKeyword(@RequestParam("keyword") String keyword) {
        List<QueryGoodsVO> list = goodsService.queryGoodsByKeyword(keyword);
        return BaseResponse.success(list);
    }

    /**
     * 根据id删除商品
     */
    @DeleteMapping("/deleteGoods")
    @ApiOperation(value = "根据id删除商品")
    public BaseResponse<String> deleteGoods(@RequestParam("goodsId") Long goodsId) {
        boolean result = goodsService.deleteGoods(goodsId);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 分页查询物品
     */
    @GetMapping("/queryGoodsByPage")
    @ApiOperation(value = "分页查询物品")
    public BaseResponse<Map<String, Object>> queryGoodsByPage(@RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        Map<String, Object> map = goodsService.queryGoodsByPage(current, size);
        return BaseResponse.success(map);
    }
}
