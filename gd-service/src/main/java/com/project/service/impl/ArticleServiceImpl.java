package com.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.project.domain.Article;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.ArticleMapper;
import com.project.service.ArticleService;
import com.project.utils.RedisUtil;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import com.project.vo.article.QueryArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
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
     * @param userId 用户id
     */
    @Override
    public List<QueryArticleVO> queryArticle(Long userId) {
        // 校验参数
        userId = userId == null ? UserContext.getUserId() : userId;
        // todo 查询 Redis 记录
        // 查询数据库
        try {
            return articleMapper.queryUser(userId);
        } catch (Exception e) {
            log.error("查询用户动态----->数据库查询失败");
            throw new RuntimeException(e);
        }
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
        try {
            return articleMapper.queryOne(articleId);
        } catch (Exception e) {
            log.error("根据id查询动态信息----->查询数据库失败");
            throw new RuntimeException(e);
        }
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
        // 获取自己的id
        Long userId = UserContext.getUserId();
        // 查询数据库记录
        try {
            return articleMapper.queryArticleOfSchool(userId);
        } catch (Exception e) {
            log.error("查询校园动态----->数据库查询失败");
            throw new RuntimeException(e);
        }
    }
}
