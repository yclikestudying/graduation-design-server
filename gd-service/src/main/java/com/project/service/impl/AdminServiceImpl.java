package com.project.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.constant.RoleConstant;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.AdminMapper;
import com.project.mapper.ArticleMapper;
import com.project.service.AdminService;
import com.project.service.LikesService;
import com.project.utils.UserContext;
import com.project.vo.article.QueryArticleVO;
import com.project.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private LikesService likesService;

    /**
     * 查询普通用户
     */
    @Override
    public Map<String, Object> queryUser(Integer current, Integer size) {
        // 校验参数
        if (current <= 0 || size <= 0) {
            log.error("分页查询用户 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 数据库查询
        Page<UserVO> page = new Page<>(current, size);
        Page<UserVO> userVOPage = adminMapper.queryUser(page);
        Map<String, Object> map = new HashMap<>();
        map.put("user", userVOPage.getRecords());
        map.put("total", userVOPage.getTotal());
        return map;
    }

    /**
     * 模糊查询普通用户
     */
    @Override
    public Map<String, Object> queryLikeUser(String keyword, Integer current, Integer size) {
        // 校验参数
        if (current <= 0 || size <= 0 || StringUtils.isBlank(keyword)) {
            log.error("模糊查询普通用户 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 数据库查询
        Page<UserVO> page = new Page<>(current, size);
        Page<UserVO> userVOPage = adminMapper.queryLikeUser(page, keyword);
        Map<String, Object> map = new HashMap<>();
        map.put("user", userVOPage.getRecords());
        map.put("total", userVOPage.getTotal());
        return map;
    }

    /**
     * 删除单个用户
     */
    @Override
    public boolean deleteUser(Long userId) {
        // 参数校验
        if (userId <= 0) {
            log.error("删除单个用户 -----> 用户id错误");
            throw new BusinessExceptionHandler(400, "用户id错误");
        }

        // 获取当前用户的id
        Long currentUserId = UserContext.getUserId();
        if (userId.equals(currentUserId)) {
            log.error("删除单个用户 -----> 不能删除自己");
            throw new BusinessExceptionHandler(400, "不能删除自己");
        }

        // 执行操作
        return adminMapper.deleteUser(userId);
    }

    /**
     * 批量删除用户
     */
    @Override
    public boolean deleteUserBatch(List<Long> userIdList) {
        // 参数校验
        if (userIdList.isEmpty()) {
            log.error("批量删除用户 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 获取当前用户的id
        Long userId = UserContext.getUserId();
        if (userIdList.contains(userId)) {
            log.error("批量删除用户 -----> 不能删除自己");
            throw new BusinessExceptionHandler(400, "不能删除自己");
        }

        // 执行操作
        return adminMapper.deleteUserBatch(userIdList);
    }

    /**
     * 设置用户为管理员
     */
    @Override
    public boolean settingAdmin(Long userId) {
        // 校验参数
        if (userId <= 0) {
            log.error("设置用户为管理员 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 查询用户权限
        String userRole = adminMapper.getUserRole(userId);
        if (Objects.equals(userRole, RoleConstant.ROLE_ADMIN)) {
            log.error("设置用户为管理员 -----> 当前用户已经是管理员");
            throw new BusinessExceptionHandler(400, "当前用户已经是管理员");
        }

        // 设置成管理员
        return adminMapper.settingAdmin(userId);
    }

    /**
     * 分页查询管理员
     */
    @Override
    public Map<String, Object> queryAdmin(Integer current, Integer size) {
        // 校验参数
        if (current <= 0 || size <= 0) {
            log.error("分页查询管理员 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 数据库查询
        Page<UserVO> page = new Page<>(current, size);
        Page<UserVO> userVOPage = adminMapper.queryAdmin(page);
        Map<String, Object> map = new HashMap<>();
        map.put("admin", userVOPage.getRecords());
        map.put("total", userVOPage.getTotal());
        return map;
    }

    /**
     * 模糊查询管理员
     */
    @Override
    public Map<String, Object> queryLikeAdmin(String keyword, Integer current, Integer size) {
        // 校验参数
        if (current <= 0 || size <= 0 || StringUtils.isBlank(keyword)) {
            log.error("模糊查询管理员 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 数据库查询
        Page<UserVO> page = new Page<>(current, size);
        Page<UserVO> userVOPage = adminMapper.queryLikeAdmin(page, keyword);
        Map<String, Object> map = new HashMap<>();
        map.put("admin", userVOPage.getRecords());
        map.put("total", userVOPage.getTotal());
        return map;
    }

    /**
     * 设置管理员为普通用户
     */
    @Override
    public boolean settingUser(Long userId) {
        // 校验参数
        if (userId <= 0) {
            log.error("设置管理员为普通用户 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 查询用户权限
        String userRole = adminMapper.getUserRole(userId);
        if (Objects.equals(userRole, RoleConstant.ROLE_USER)) {
            log.error("设置管理员为普通用户 -----> 当前用户已经是普通用户");
            throw new BusinessExceptionHandler(400, "当前用户已经是普通用户");
        }

        // 设置成普通用户
        return adminMapper.settingUser(userId);
    }

    /**
     * 分页查询动态
     */
    @Override
    public Map<String, Object> queryArticle(Integer current, Integer size) {
        // 查询数据库记录
        List<QueryArticleVO> queryArticleVOS;
        Page<QueryArticleVO> queryArticleVOPage;
        try {
            Page<QueryArticleVO> page = new Page<>(current, size);
            queryArticleVOPage = articleMapper.queryArticleByPage(page);
            queryArticleVOS = queryArticleVOPage.getRecords();
        } catch (Exception e) {
            log.error("查询校园动态----->数据库查询失败");
            throw new RuntimeException(e);
        }

        // 根据动态id查询点赞数以及自己是否进行点赞
        if (!queryArticleVOS.isEmpty()) {
            queryArticleVOS = queryArticleVOS.stream().peek(queryArticleVO -> {
                // 查询动态点赞数
                Integer count = likesService.queryLikeCount(queryArticleVO.getId());
                queryArticleVO.setLikeCount(count);
            }).collect(Collectors.toList());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("article", queryArticleVOS);
        map.put("total", queryArticleVOPage.getTotal());
        return map;
    }
}
