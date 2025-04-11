package com.project.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.constant.RoleConstant;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.AdminMapper;
import com.project.service.AdminService;
import com.project.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

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

        // 查询用户是否已经是管理员
        String userRole = adminMapper.isAdmin(userId);
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
}
