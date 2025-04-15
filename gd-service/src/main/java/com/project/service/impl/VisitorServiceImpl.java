package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Visitor;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.VisitorMapper;
import com.project.service.VisitorService;
import com.project.utils.UserContext;
import com.project.vo.visit.QueryVisitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor>
        implements VisitorService {
    @Resource
    private VisitorMapper visitorMapper;

    /**
     * 添加访客记录
     */
    @Override
    public boolean addVisitor(Long visitorId, Long visitedId) {
        // 校验参数
        if (visitorId <= 0 || visitedId <= 0) {
            log.error("添加访客记录 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 查询在此之前是否存在 visitorId 用户对 visitedId 用户的访客记录
        QueryWrapper<Visitor> queryWrapper = new QueryWrapper<Visitor>().eq("visitor_id", visitorId).eq("visited_id", visitedId);
        Visitor one = visitorMapper.selectOne(queryWrapper);
        if (one != null) {
            // 删除之前的访客记录
            int delete = visitorMapper.delete(queryWrapper);
            if (delete == 0) {
                log.error("添加访客记录 -----> 删除旧访客记录失败");
                throw new BusinessExceptionHandler(400, "删除旧访客记录失败");
            }
        }

        // 保存至数据库
        Visitor visitor = new Visitor();
        visitor.setVisitorId(visitorId);
        visitor.setVisitedId(visitedId);
        return visitorMapper.insert(visitor) > 0;
    }

    /**
     * 删除当前用户全部访客记录
     */
    @Override
    public boolean deleteVisitor() {
        // 获取当前用户的id
        Long userId = UserContext.getUserId();

        // 删除当前用户全部访客记录
        return visitorMapper.delete(new QueryWrapper<Visitor>().eq("visited_id", userId)) > 0;
    }

    /**
     * 查询访客记录
     */
    @Override
    public List<QueryVisitVO> queryVisitor() {
        // 获取当前用户的id
        Long userId = UserContext.getUserId();

        // 根据用户id查询当前用户的访客记录
        return visitorMapper.queryVisitor(userId);
    }
}
