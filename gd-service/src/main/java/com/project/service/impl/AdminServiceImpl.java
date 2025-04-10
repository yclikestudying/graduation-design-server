package com.project.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.AdminMapper;
import com.project.service.AdminService;
import com.project.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
