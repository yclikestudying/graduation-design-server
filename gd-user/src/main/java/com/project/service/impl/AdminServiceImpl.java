package com.project.service.impl;

import com.google.gson.Gson;
import com.project.constant.RedisConstant;
import com.project.constant.RoleConstant;
import com.project.domain.User;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.AdminMapper;
import com.project.service.AdminService;
import com.project.utils.MD5Util;
import com.project.utils.RedisUtil;
import com.project.utils.TokenUtil;
import com.project.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private final Gson gson = new Gson();

    /**
     * 管理员登录
     */
    @Override
    public Map<String, Object> login(String userPhone, String userPassword) {
        // 校验手机号和密码
        if (StringUtils.isAnyBlank(userPhone, userPassword)) {
            log.error("管理员登录 -----> 手机号和密码不能为空");
            throw new BusinessExceptionHandler(400, "手机号和密码不能为空");
        }

        // 查询该用户是否存在
        UserVO userVO = adminMapper.queryUser(userPhone);
        if (userVO == null) {
            log.error("管理员登录 -----> 用户不存在");
            throw new BusinessExceptionHandler(400, "用户不存在");
        }

        // 校验密码是否一致
        if (!Objects.equals(MD5Util.calculateMD5(userPassword), userVO.getUserPassword())) {
            log.error("管理员登录 -----> 手机号或密码错误");
            throw new BusinessExceptionHandler(400, "手机号或密码错误");
        }

        // 校验是否是管理员权限
        if (!Objects.equals(RoleConstant.ROLE_ADMIN, userVO.getUserRole())) {
            log.error("管理员登录 -----> 不是管理员权限");
            throw new BusinessExceptionHandler(400, "不是管理员权限");
        }
        // 存储 Redis
        String token = TokenUtil.createToken(userVO.getId(), userVO.getUserPhone());
        redisUtil.setRedisData(RedisConstant.getRedisKey(RedisConstant.USER_TOKEN, userVO.getId()), token);
        redisUtil.setRedisData(RedisConstant.getRedisKey(RedisConstant.USER_INFO, userVO.getId()), gson.toJson(userVO));
        Map<String, Object> map = new HashMap<>();
        map.put("user", userVO);
        // 生成token
        map.put("token", token);
        return map;
    }
}
