package com.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ActivityRelation;
import com.project.vo.activityRelation.ActivityRelationVO;

public interface ActivityRelationService extends IService<ActivityRelation> {
    /**
     * 加入活动
     */
    boolean addActivity(Long activityId);
    /**
     * 查询当前活动人数
     */
    Integer queryCount(Long activityId);
}
