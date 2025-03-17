package com.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Goods;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.GoodsMapper;
import com.project.service.GoodsService;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
        implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 上传商品
     */
    @Override
    public boolean upload(MultipartFile file, String text, String oldPrice, String price) {
        // 获取自己id
        Long userId = UserContext.getUserId();
        // 校验参数
        if (StringUtils.isAnyBlank(text, oldPrice, price)) {
            log.error("上传商品 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 上传阿里云
        String link = null;
        try {
            link = Upload.uploadAvatar(file, "goods");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Goods goods = new Goods();
        goods.setUserId(userId);
        goods.setGoodsContent(text);
        goods.setGoodsPhotos(link);
        goods.setGoodsOldPrice(Integer.valueOf(oldPrice));
        goods.setGoodsPrice(Integer.valueOf(price));
        try {
            return goodsMapper.insert(goods) > 0;
        } catch (Exception e) {
            log.error("上传商品 -----> 插入数据库错误");
            throw new RuntimeException(e);
        }
    }
}
