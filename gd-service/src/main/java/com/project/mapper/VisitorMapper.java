package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.Visitor;
import com.project.vo.visit.QueryVisitVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisitorMapper extends BaseMapper<Visitor> {
    /**
     * 查询访客记录
     */
    List<QueryVisitVO> queryVisitor(@Param("userId") Long userId);
}
