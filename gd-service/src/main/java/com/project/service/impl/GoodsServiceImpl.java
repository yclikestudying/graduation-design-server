package com.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Goods;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.GoodsMapper;
import com.project.service.GoodsService;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import com.project.vo.article.QueryArticleVO;
import com.project.vo.goods.QueryGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

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
    public boolean upload(MultipartFile file, String title, String text, String oldPrice, String price) {
        // 获取自己id
        Long userId = UserContext.getUserId();
        // 校验参数
        if (StringUtils.isAnyBlank(title, text, oldPrice, price)) {
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
        goods.setGoodsTitle(title);
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

    /**
     * 查询个人商品
     */
    @Override
    public List<QueryGoodsVO> queryGoods(Long userId) {
        // 校验id
        userId = checkUserId(userId);
        // 查询数据库记录
        try {
            return goodsMapper.queryGoods(userId);
        } catch (Exception e) {
            log.error("查询个人商品 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询全部商品
     */
    @Override
    public List<QueryGoodsVO> queryAllGoods() {
        try {
            return goodsMapper.queryGoods(null);
        } catch (Exception e) {
            log.error("查询个人商品 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 关键字模糊查询商品
     */
    @Override
    public List<QueryGoodsVO> queryGoodsByKeyword(String keyword) {
        // 校验
        if (StringUtils.isBlank(keyword)) {
            log.error("关键字模糊查询商品 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 查询数据库
        try {
            return goodsMapper.queryGoodsByKeyword(keyword);
        } catch (Exception e) {
            log.error("关键字模糊查询商品 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id删除商品
     */
    @Override
    public boolean deleteGoods(Long goodsId) {
        // 校验参数
        if (goodsId <= 0) {
            log.error("根据id删除商品----->参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 删除数据库动态记录
        int result;
        try {
            result = goodsMapper.deleteById(goodsId);
        } catch (Exception e) {
            log.error("根据id删除商品----->数据库删除失败");
            throw new RuntimeException(e);
        }
        if (result == 0) {
            log.error("根据id删除商品 -----> 商品不存在");
            throw new BusinessExceptionHandler(400, "商品不存在");
        }
        return result > 0;
    }

    /**
     * 校验是根据自己的id还是其他人的id进行操作
     */
    private Long checkUserId(Long userId) {
        return userId == null ? UserContext.getUserId() : userId;
    }
}
