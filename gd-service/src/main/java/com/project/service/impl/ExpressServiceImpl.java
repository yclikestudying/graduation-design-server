package com.project.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Express;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.ExpressMapper;
import com.project.service.ExpressService;
import com.project.utils.UserContext;
import com.project.vo.express.QueryExpressVO;
import com.project.vo.goods.QueryGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 分页查询跑腿服务
     */
    @Override
    public Map<String, Object> queryExpressByPage(Integer current, Integer size) {
        // 校验参数
        if (current <= 0 || size <= 0) {
            log.error("分页查询跑腿服务 -----> 分页参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 进行查询
        Page<QueryExpressVO> page = new Page<>(current, size);
        Page<QueryExpressVO> queryExpressVOPage = expressMapper.queryExpressByPage(page);
        Map<String, Object> map = new HashMap<>();
        map.put("express", queryExpressVOPage.getRecords());
        map.put("total", queryExpressVOPage.getTotal());
        return map;
    }

    /**
     * 批量删除跑腿服务
     */
    @Override
    public boolean deleteExpressBatch(List<Long> expressIdList) {
        // 参数校验
        if (expressIdList.isEmpty()) {
            log.error("批量删除物品 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 执行操作
        return expressMapper.deleteBatchIds(expressIdList) > 0;
    }

    /**
     * 按时间搜索跑腿服务
     */
    @Override
    public Map<String, Object> queryExpressByTime(String time, Integer current, Integer size) {
        // 校验参数
        if (StringUtils.isBlank(time)) {
            log.error("按时间搜索跑腿服务 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 数据库查询
        Page<QueryExpressVO> page = new Page<>(current, size);
        Page<QueryExpressVO> queryArticleVOPage = expressMapper.queryExpressByTime(page, time);
        Map<String, Object> map = new HashMap<>();
        map.put("express", queryArticleVOPage.getRecords());
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
