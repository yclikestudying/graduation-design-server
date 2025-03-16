package com.project.interceptor;

import com.google.gson.Gson;
import com.project.common.CodeEnum;
import com.project.constant.RedisConstant;
import com.project.exception.BusinessExceptionHandler;
import com.project.utils.RedisUtil;
import com.project.utils.TokenUtil;
import com.project.utils.UserContext;
import com.project.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private final Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是浏览器的预检请求，那么直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        // 从请求头中获取Token
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            // 如果Token为空，返回1004未用户未登录态码
            log.error("请求拦截器----->请求头 token 校验失败");
            throw new BusinessExceptionHandler(CodeEnum.getByCode(1004)); // 用户未登录
        }
        // 解析token
        Map<String, Object> map = TokenUtil.parseToken(token);
        Long userId = (Long) map.get("userId");
        // 查询 Redis，判断当前用户是否真的登录
        String redisKey = RedisConstant.getRedisKey(RedisConstant.USER_TOKEN, userId);
        String redisData = redisUtil.getRedisData(redisKey);
        if (redisData == null || !Objects.equals(redisData, token)) {
            log.error("请求拦截器----->Redis token 校验失败");
            throw new BusinessExceptionHandler(CodeEnum.getByCode(1004)); // 用户未登录
        }
        // 验证成功，获取用户个人信息
        String user = redisUtil.getRedisData(RedisConstant.getRedisKey(RedisConstant.USER_INFO, userId));
        UserContext.setUser(gson.fromJson(user, UserVO.class));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear(); // 避免内容泄漏
    }
}

