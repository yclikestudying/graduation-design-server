package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Article;
import com.project.vo.article.QueryArticleVO;

import java.util.List;
import java.util.Map;

public interface ArticleService extends IService<Article> {
    /**
     * 发布动态
     */
    boolean upload(Map<Integer, Object> map, String text);

    /**
     * 查询用户动态
     * @param userId 用户id
     */
    List<QueryArticleVO> queryArticle(Long userId);

    /**
     * 根据id查询动态信息
     * @param articleId 动态id
     */
    QueryArticleVO queryOne(Long articleId);

    /**
     * 根据id删除动态
     * @param articleId 动态id
     */
    boolean deleteArticle(Long articleId);

    /**
     * 查询校园动态
     */
    List<QueryArticleVO> queryArticleOfSchool();

    /**
     * 查询我的动态数量
     */
    Integer articleCount();
}
