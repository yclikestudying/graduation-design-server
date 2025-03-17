package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Goods;
import com.project.vo.goods.QueryGoodsVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsService extends IService<Goods> {
    /**
     * 上传商品
     */
    boolean upload(MultipartFile file, String text, String oldPrice, String price);

    /**
     * 查询个人商品
     */
    List<QueryGoodsVO> queryGoods(Long userId);
}
