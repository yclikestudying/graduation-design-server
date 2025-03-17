package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.GoodsService;
import com.project.vo.goods.QueryGoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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
                                       @RequestParam("text") String text,
                                       @RequestParam("oldPrice") String oldPrice,
                                       @RequestParam("price") String price) {
        boolean result = goodsService.upload(file, text, oldPrice, price);
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
}
