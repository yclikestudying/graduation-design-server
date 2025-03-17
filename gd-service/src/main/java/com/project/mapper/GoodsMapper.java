package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.Goods;
import com.project.vo.article.QueryArticleVO;
import com.project.vo.goods.QueryGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 查询个人商品
     */
    List<QueryGoodsVO> queryGoods(@Param("userId") Long userId);
    /**
     * 关键字模糊查询动态
     */
    List<QueryGoodsVO> queryGoodsByKeyword(@Param("keyword") String keyword);
}
