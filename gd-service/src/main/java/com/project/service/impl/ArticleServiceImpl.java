package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.project.domain.Article;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.ArticleMapper;
import com.project.service.ArticleService;
import com.project.service.LikesService;
import com.project.utils.RedisUtil;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import com.project.vo.article.QueryArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private LikesService likesService;
    @Resource
    private ThreadPoolTaskExecutor executor;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private final Gson gson = new Gson();

    /**
     * 发布动态
     */
    @Override
    public boolean upload(Map<Integer, Object> map, String text) {
        // 验证
        if (map.isEmpty() && StringUtils.isBlank(text)) {
            log.error("发布动态----->参数不能为空");
            throw new BusinessExceptionHandler(400, "参数不能为空");
        }
        // 上传阿里云oos
        if (!map.isEmpty()) {
            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                Integer key = entry.getKey();
                MultipartFile file = (MultipartFile) entry.getValue();
                // 并发上传
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    try {
                        return Upload.uploadAvatar(file, "article");
                    } catch (IOException e) {
                        log.error("发布动态----->上传阿里云失败");
                        throw new RuntimeException(e);
                    }
                }, executor);

                try {
                    // 阻塞当前线程，等所有线程全部完成，才获取到结果
                    map.put(key, future.join());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // 获取新地址到list集合中
        List<Object> list = new ArrayList<>(map.values());
        Article article = new Article();
        article.setUserId(UserContext.getUserId());
        article.setArticleContent(text);
        article.setArticlePhotos(gson.toJson(list));
        int insert = articleMapper.insert(article);
        if (insert == 0) {
            log.error("发布动态----->插入数据库失败");
            throw new BusinessExceptionHandler(200, "发布动态失败");
        }
        return true;
    }

    /**
     * 查询用户动态
     * @param id 用户id
     */
    @Override
    public List<QueryArticleVO> queryArticle(Long id) {
        // 校验参数
        Long userId = checkUserId(id);
        // todo 查询 Redis 记录
        // 查询数据库
        List<QueryArticleVO> queryArticleVOS;
        try {
            queryArticleVOS = articleMapper.queryUser(userId);

        } catch (Exception e) {
            log.error("查询用户动态----->数据库查询失败");
            throw new RuntimeException(e);
        }

        // 根据动态id查询点赞数以及自己是否进行点赞
        if (!queryArticleVOS.isEmpty()) {
            return queryArticleVOS.stream().peek(queryArticleVO -> {
                // 查询动态点赞数
                Integer count = likesService.queryLikeCount(queryArticleVO.getId());
                queryArticleVO.setLikeCount(count);
                // 查询是否对当前动态进行过点赞
                boolean result = likesService.queryUserLike(userId, queryArticleVO.getId());
                queryArticleVO.setLike(result);
            }).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 根据id查询动态信息
     * @param articleId 动态id
     */
    @Override
    public QueryArticleVO queryOne(Long articleId) {
        // 校验参数
        if (articleId <= 0) {
            log.error("根据id查询动态信息----->参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 查询数据库
        QueryArticleVO queryArticleVO;
        try {
            queryArticleVO = articleMapper.queryOne(articleId);
        } catch (Exception e) {
            log.error("根据id查询动态信息----->查询数据库失败");
            throw new RuntimeException(e);
        }

        // 根据动态id查询点赞数以及自己是否进行点赞
        // 查询动态点赞数
        Integer count = likesService.queryLikeCount(queryArticleVO.getId());
        queryArticleVO.setLikeCount(count);
        // 查询是否对当前动态进行过点赞
        Long userId = UserContext.getUserId();
        boolean result = likesService.queryUserLike(userId, queryArticleVO.getId());
        queryArticleVO.setLike(result);
        return queryArticleVO;
    }

    /**
     * 根据id删除动态
     * @param articleId 动态id
     */
    @Override
    public boolean deleteArticle(Long articleId) {
        // 校验参数
        if (articleId <= 0) {
            log.error("根据id删除动态----->参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 删除数据库动态记录
        try {
            return articleMapper.deleteById(articleId) > 0;
        } catch (Exception e) {
            log.error("根据id删除动态----->数据库删除失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询校园动态
     */
    @Override
    public List<QueryArticleVO> queryArticleOfSchool() {
        // 查询数据库记录
        List<QueryArticleVO> queryArticleVOS;
        try {
            queryArticleVOS = articleMapper.queryArticleOfSchool();
        } catch (Exception e) {
            log.error("查询校园动态----->数据库查询失败");
            throw new RuntimeException(e);
        }

        // 根据动态id查询点赞数以及自己是否进行点赞
        if (!queryArticleVOS.isEmpty()) {
            Long userId = UserContext.getUserId();
            return queryArticleVOS.stream().peek(queryArticleVO -> {
                // 查询动态点赞数
                Integer count = likesService.queryLikeCount(queryArticleVO.getId());
                queryArticleVO.setLikeCount(count);
                // 查询是否对当前动态进行过点赞
                boolean result = likesService.queryUserLike(userId, queryArticleVO.getId());
                queryArticleVO.setLike(result);
            }).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 查询我的动态数量
     */
    @Override
    public Integer articleCount() {
        // 获取我的id
        Long userId = UserContext.getUserId();
        // 查询数据库
        try {
            return articleMapper.selectCount(new QueryWrapper<Article>().eq("user_id", userId));
        } catch (Exception e) {
            log.error("查询我的动态数量----->数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询关注动态
     */
    @Override
    public List<QueryArticleVO> queryArticleOfAttention() {
        // 获取我的id
        Long userId = UserContext.getUserId();
        // 查询关注用户id
        List<Long> idList = null;
        try {
            idList = articleMapper.queryFriendIds(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (idList.isEmpty()) {
            log.error("查询关注动态 -----> 没有关注动态");
            throw new BusinessExceptionHandler(400, "暂无关注动态");
        }
        // 根据id查询关注用户动态
        try {
            return articleMapper.queryArticleOfAttention(idList);
        } catch (Exception e) {
            log.error("查询关注动态 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 关键字模糊查询动态
     */
    @Override
    public List<QueryArticleVO> queryArticleByKeyword(String keyword) {
        // 校验
        if (StringUtils.isBlank(keyword)) {
            log.error("关键字模糊查询动态 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 查询数据库
        List<QueryArticleVO> queryArticleVOS;
        try {
            queryArticleVOS = articleMapper.queryArticleByKeyword(keyword);
        } catch (Exception e) {
            log.error("关键字模糊查询动态 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }

        // 根据动态id查询点赞数以及自己是否进行点赞
        if (!queryArticleVOS.isEmpty()) {
            Long userId = UserContext.getUserId();
            return queryArticleVOS.stream().peek(queryArticleVO -> {
                // 查询动态点赞数
                Integer count = likesService.queryLikeCount(queryArticleVO.getId());
                queryArticleVO.setLikeCount(count);
                // 查询是否对当前动态进行过点赞
                boolean result = likesService.queryUserLike(userId, queryArticleVO.getId());
                queryArticleVO.setLike(result);
            }).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 批量删除动态
     */
    @Override
    public boolean deleteArticleBatch(List<Long> articleIdList) {
        // 参数校验
        if (articleIdList.isEmpty()) {
            log.error("批量删除动态 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 执行操作
        return articleMapper.deleteBatchIds(articleIdList) > 0;
    }

    /**
     * 按时间搜索发布动态
     */
    @Override
    public Map<String, Object> queryArticleByTime(String time, Integer current, Integer size) {
        // 校验参数
        if (StringUtils.isBlank(time)) {
            log.error("按时间搜索发布动态 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 数据库查询
        Page<QueryArticleVO> page = new Page<>(current, size);
        Page<QueryArticleVO> queryArticleVOPage = articleMapper.queryArticleByTime(page, time);
        Map<String, Object> map = new HashMap<>();
        map.put("article", queryArticleVOPage.getRecords());
        map.put("total", queryArticleVOPage.getTotal());
        return map;
    }

    /**
     * 校验是根据自己的id还是其他人的id进行操作
     */
    private Long checkUserId(Long userId) {
        return userId == null ? UserContext.getUserId() : userId;
    }
}
