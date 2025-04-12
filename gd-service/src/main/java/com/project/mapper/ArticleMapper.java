package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    List<QueryArticleVO> queryArticleOfSchool();

    /**
     * 查询关注用户id
     */
    List<Long> queryFriendIds(@Param("userId") Long userId);

    /**
     * 查询关注动态
     */
    List<QueryArticleVO> queryArticleOfAttention(@Param("idList") List<Long> idList);

    /**
     * 关键字模糊查询动态
     */
    List<QueryArticleVO> queryArticleByKeyword(@Param("keyword") String keyword);

    /**
     * 分页查询动态
     */
    Page<QueryArticleVO> queryArticleByPage(Page<QueryArticleVO> page);

    /**
     * 按时间搜索发布动态
     */
    Page<QueryArticleVO> queryArticleByTime(Page<QueryArticleVO> page, @Param("time")String time);
}
