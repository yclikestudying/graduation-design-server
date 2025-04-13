package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.domain.Lost;
import com.project.vo.lost.QueryLostVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LostMapper extends BaseMapper<Lost> {
    /**
     * 查询用户寻物启事
     */
    List<QueryLostVO> queryLost(@Param("userId") Long userId, @Param("keyword") String keyword);

    /**
     * 分页查询寻物启事
     */
    Page<QueryLostVO> queryLostByPage(Page<QueryLostVO> page);

    /**
     * 按时间搜索寻物启事
     */
    Page<QueryLostVO> queryGoodsByTime(Page<QueryLostVO> page, @Param("time") String time);
}
