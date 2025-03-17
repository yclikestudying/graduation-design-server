package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.Express;
import com.project.vo.express.QueryExpressVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpressMapper extends BaseMapper<Express> {
    /**
     * 查询自己的跑腿任务
     */
    List<QueryExpressVO> queryExpress(@Param("userId") Long userId);
}
