package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.Article;
import com.project.vo.article.QueryArticleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 查询用户动态
     * @param userId 用户id
     */
    List<QueryArticleVO> queryUser(@Param("userId") Long userId);
    /**
     * 根据id查询动态信息
     * @param articleId 动态id
     */
    QueryArticleVO queryOne(@Param("articleId") Long articleId);
    /**
     * 查询校园动态
     */
    List<QueryArticleVO> queryArticleOfSchool(@Param("userId") Long userId);
}
