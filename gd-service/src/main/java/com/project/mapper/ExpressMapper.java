package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.domain.Express;
import com.project.vo.express.QueryExpressVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpressMapper extends BaseMapper<Express> {
    /**
     * 查询自己的跑腿任务
     */
    List<QueryExpressVO> queryExpress(@Param("userId") Long userId, @Param("keyword") String keyword);

    /**
     * 分页查询跑腿服务
     */
    Page<QueryExpressVO> queryExpressByPage(Page<QueryExpressVO> page);

    /**
     * 按时间搜索跑腿服务
     */
    Page<QueryExpressVO> queryExpressByTime(Page<QueryExpressVO> page, @Param("time") String time);
}
