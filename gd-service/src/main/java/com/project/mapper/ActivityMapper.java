package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.Activity;
import com.project.vo.activity.QueryActivityVO;
import com.project.vo.activity.QueryOneActivityVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityMapper extends BaseMapper<Activity> {
    /**
     * 查询活动
     */
    List<QueryActivityVO> queryActivity(@Param("userId") Long userId, @Param("keyword") String keyword);

    /**
     * 根据活动id查询活动
     */
    QueryOneActivityVO queryActivityById(@Param("activityId") Long activityId);
}
