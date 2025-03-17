package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Goods;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsService extends IService<Goods> {
    /**
     * 上传商品
     */
    boolean upload(MultipartFile file, String text, String oldPrice, String price);
}
