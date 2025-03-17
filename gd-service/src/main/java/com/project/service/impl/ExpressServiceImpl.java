package com.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Express;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.ExpressMapper;
import com.project.service.ExpressService;
import com.project.utils.UserContext;
import com.project.vo.express.QueryExpressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ExpressServiceImpl extends ServiceImpl<ExpressMapper, Express>
        implements ExpressService {
    @Resource
    private ExpressMapper expressMapper;

    /**
     * 发布跑腿任务
     */
    @Override
    public boolean upload(String text) {
        // 获取我的id
        Long userId = UserContext.getUserId();
        // 校验参数
        if (StringUtils.isBlank(text)) {
            log.error("发布跑腿服务 -----> 参数不能为空");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 保存数据库
        Express express = new Express();
        express.setUserId(userId);
        express.setExpressContent(text);
        try {
            return expressMapper.insert(express) > 0;
        } catch (Exception e) {
            log.error("发布跑腿服务 -----> 数据库插入失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询自己的跑腿任务
     */
    @Override
    public List<QueryExpressVO> queryExpress(Long userId) {
        userId = checkUserId(userId);
        // 查询数据库
        try {
            return expressMapper.queryExpress(userId, null);
        } catch (Exception e) {
            log.error("查询自己的跑腿任务 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有跑腿任务
     */
    @Override
    public List<QueryExpressVO> queryAllExpress() {
        // 查询数据库
        try {
            return expressMapper.queryExpress(null, null);
        } catch (Exception e) {
            log.error("查询自己的跑腿任务 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteExpress(Long expressId) {
        // 校验参数
        if (expressId <= 0) {
            log.error("根据id删除跑腿任务----->参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 删除数据库动态记录
        try {
            return expressMapper.deleteById(expressId) > 0;
        } catch (Exception e) {
            log.error("根据id删除跑腿任务----->数据库删除失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 关键字模糊查询跑腿任务
     */
    @Override
    public List<QueryExpressVO> queryExpressByKeyword(String keyword) {
        // 校验
        if (StringUtils.isBlank(keyword)) {
            log.error("关键字模糊查询跑腿任务 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 查询数据库
        try {
            return expressMapper.queryExpress(null, keyword);
        } catch (Exception e) {
            log.error("关键字模糊查询跑腿任务 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验是根据自己的id还是其他人的id进行操作
     */
    private Long checkUserId(Long userId) {
        return userId == null ? UserContext.getUserId() : userId;
    }
}
