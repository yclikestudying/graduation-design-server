package com.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ActivityRelation;
import com.project.vo.activityRelation.ActivityRelationVO;

import java.util.List;
import java.util.Set;

public interface ActivityRelationService extends IService<ActivityRelation> {
    /**
     * 加入活动
     */
    boolean addActivity(Long activityId);
    /**
     * 查询当前活动人数
     */
    Integer queryCount(Long activityId);

    /**
     * 查询活动数量
     */
    Integer queryCount();

    /**
     * 查询该活动（群聊）下所有用户的id
     */
    List<Long> getUserIdsByActivityId(Long activityId);

    /**
     * 查询用户所参加的群聊id
     */
    Set<Long> queryActivityIdList(Long userId);
}
