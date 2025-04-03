package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Likes;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.LikesMapper;
import com.project.service.ArticleService;
import com.project.service.LikesService;
import com.project.utils.UserContext;
import com.project.vo.article.QueryArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
        implements LikesService {
    @Resource
    private LikesMapper likesMapper;
    @Resource
    private ArticleService articleService;

    /**
     * 点赞
     */
    @Override
    public boolean like(Long articleId) {
        // 校验动态id
        if (articleId <= 0) {
            log.error("点赞 -----> 点赞动态id参数异常");
            throw new BusinessExceptionHandler(400, "点赞动态id参数异常");
        }

        // 查询该动态是否存在
        QueryArticleVO queryArticleVO = articleService.queryOne(articleId);
        if (queryArticleVO == null) {
            log.error("点赞 -----> 该动态不存在");
            throw new BusinessExceptionHandler(400, "该动态不存在或已被删除");
        }

        // 查询是否已经被点赞
        Likes likes = likesMapper.selectOne(new QueryWrapper<Likes>().eq("like_article_id", articleId));
        if (likes != null) {
            log.error("点赞 -----> 不能重复点赞");
            throw new BusinessExceptionHandler(400, "不能重复点赞");
        }

        // 进行点赞操作
        Likes likes1 = new Likes();
        likes1.setLikeArticleId(articleId);
        likes1.setLikeUserId(UserContext.getUserId());
        return likesMapper.insert(likes1) > 0;
    }

    /**
     * 取消点赞
     */
    @Override
    public boolean cancelLike(Long articleId) {
        // 校验动态id
        if (articleId <= 0) {
            log.error("取消点赞 -----> 点赞动态id参数异常");
            throw new BusinessExceptionHandler(400, "点赞动态id参数异常");
        }

        // 查询该动态是否存在
        QueryArticleVO queryArticleVO = articleService.queryOne(articleId);
        if (queryArticleVO == null) {
            log.error("取消点赞 -----> 该动态不存在");
            throw new BusinessExceptionHandler(400, "该动态不存在或已被删除");
        }

        // 查询是否已经被点赞
        Likes likes = likesMapper.selectOne(new QueryWrapper<Likes>().eq("like_article_id", articleId));
        if (likes == null) {
            log.error("取消点赞 -----> 未进行点赞");
            throw new BusinessExceptionHandler(400, "未进行点赞");
        }

        // 执行取消点赞操作
        return likesMapper.delete(new QueryWrapper<Likes>().eq("like_article_id", articleId)) > 0;
    }

    /**
     * 根据动态id查询动态点赞数
     */
    @Override
    public Integer queryLikeCount(Long articleId) {
        return likesMapper.selectCount(new QueryWrapper<Likes>().eq("like_article_id", articleId));
    }

    /**
     * 查询用户是否对动态进行点赞
     */
    @Override
    public boolean queryUserLike(Long userId, Long articleId) {
        return likesMapper.selectOne(new QueryWrapper<Likes>()
                .eq("like_user_id", userId)
                .eq("like_article_id", articleId)) != null;
    }
}
