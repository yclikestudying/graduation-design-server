package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Express;
import com.project.vo.express.QueryExpressVO;

import java.util.List;

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
}
