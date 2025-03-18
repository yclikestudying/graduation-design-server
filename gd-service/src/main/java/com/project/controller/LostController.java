package com.project.controller;

import com.project.common.BaseResponse;
import com.project.dto.lost.UploadRequest;
import com.project.service.LostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lost")
@Api(tags = "寻物启事模块")
public class LostController {
    @Resource
    private LostService lostService;

    /**
     * 上传寻物启事（携带图片）
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传寻物启事（携带图片）")
    public BaseResponse<String> upload(@RequestParam("file") MultipartFile file,
                                       @RequestParam("type") String type,
                                       @RequestParam("title") String title,
                                       @RequestParam("description") String description){
        boolean result = lostService.uplaod(file, type, title, description);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 上传寻物启事（不携带图片）
     */
    @PostMapping("/uploadNoFile")
    @ApiOperation(value = "上传寻物启事（不携带图片）")
    public BaseResponse<String> uploadNoFile(@RequestBody UploadRequest uploadRequest){
        boolean result = lostService.uploadNoFile(uploadRequest);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }
}
