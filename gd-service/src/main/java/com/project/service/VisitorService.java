package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Visitor;
import com.project.vo.visit.QueryVisitVO;

import java.util.List;

public interface VisitorService extends IService<Visitor> {
    /**
     * 添加访客记录
     */
    boolean addVisitor(Long visitorId, Long visitedId);

    /**
     * 删除当前用户全部访客记录
     */
    boolean deleteVisitor();

    /**
     * 查询访客记录
     */
    List<QueryVisitVO> queryVisitor();
}
