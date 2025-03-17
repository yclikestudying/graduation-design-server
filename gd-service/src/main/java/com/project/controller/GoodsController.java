package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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
}
