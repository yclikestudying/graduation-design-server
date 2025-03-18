package com.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ActivityRelation;

public interface ActivityRelationService extends IService<ActivityRelation> {
    /**
     * 加入活动
     */
    boolean addActivity(Long activityId);
}
