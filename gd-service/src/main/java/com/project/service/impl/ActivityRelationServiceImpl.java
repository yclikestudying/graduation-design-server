package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.ActivityRelation;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.ActivityRelationMapper;
import com.project.service.ActivityRelationService;
import com.project.service.ActivityService;
import com.project.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Slf4j
public class ActivityRelationServiceImpl extends ServiceImpl<ActivityRelationMapper, ActivityRelation>
        implements ActivityRelationService {
    @Resource
    private ActivityRelationMapper activityRelationMapper;
    @Resource
    private ActivityService activityService;

    /**
     * 加入活动
     */
    @Override
    public boolean addActivity(Long activityId) {
        // 校验参数
        if (activityId <= 0) {
            log.error("加入活动 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 获取自己的id
        Long userId = UserContext.getUserId();
        synchronized ((userId + "-" + activityId).intern()) {
            // 查询是否已经加入活动
            ActivityRelation one = activityRelationMapper.selectOne(new QueryWrapper<ActivityRelation>()
                    .eq("user_id", userId)
                    .eq("activity_id", activityId));
            if (one != null) {
                log.error("加入活动 -----> 不能重复加入活动");
                throw new BusinessExceptionHandler(400, "不能重复加入活动");
            }
            // 查询活动最大人数
            Integer maxPeople = activityService.activityMaxPeople(activityId);
            // 查询活动已有人数
            Integer currentPeople = activityRelationMapper.selectCount(new QueryWrapper<ActivityRelation>()
                    .select("user_id")
                    .eq("activity_id", activityId));
            if (currentPeople >= maxPeople) {
                log.error("加入活动 -----> 人数已满");
                throw new BusinessExceptionHandler(400, "人数已满");
            }
            ActivityRelation activityRelation = new ActivityRelation();
            activityRelation.setUserId(userId);
            activityRelation.setActivityId(activityId);
            try {
                return activityRelationMapper.insert(activityRelation) > 0;
            } catch (Exception e) {
                log.error("加入活动 -----> 数据插入数据库失败");
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 校验是根据自己的id还是其他人的id进行操作
     */
    private Long checkUserId(Long userId) {
        return userId == null ? UserContext.getUserId() : userId;
    }
}




