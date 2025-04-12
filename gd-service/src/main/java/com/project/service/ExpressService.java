package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Express;
import com.project.vo.express.QueryExpressVO;

import java.util.List;
import java.util.Map;

public interface ExpressService extends IService<Express> {
    /**
     * 发布跑腿任务
     */
    boolean upload(String text);

    /**
     * 查询自己的跑腿任务
     */
    List<QueryExpressVO> queryExpress(Long userId);

    /**
     * 查询所有跑腿任务
     */
    List<QueryExpressVO> queryAllExpress();

    /**
     * 根据id删除跑腿任务
     */
    boolean deleteExpress(Long expressId);

    /**
     * 关键字模糊查询跑腿任务
     */
    List<QueryExpressVO> queryExpressByKeyword(String keyword);

    /**
     * 分页查询跑腿服务
     */
    Map<String, Object> queryExpressByPage(Integer current, Integer size);

    /**
     * 批量删除跑腿服务
     */
    boolean deleteExpressBatch(List<Long> expressIdList);

    /**
     * 按时间搜索跑腿服务
     */
    Map<String, Object> queryExpressByTime(String time, Integer current, Integer size);
}
