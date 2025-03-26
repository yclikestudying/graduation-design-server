package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.common.CodeEnum;
import com.project.domain.Activity;
import com.project.domain.ActivityRelation;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.ActivityRelationMapper;
import com.project.service.ActivityRelationService;
import com.project.service.ActivityService;
import com.project.utils.UserContext;
import com.project.vo.activityRelation.ActivityRelationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


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
            Integer currentPeople = queryCount(activityId);
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
     * 查询当前活动人数
     *
     * @return
     */
    @Override
    public Integer queryCount(Long activityId) {
        return activityRelationMapper.selectCount(new QueryWrapper<ActivityRelation>()
                .select("user_id")
                .eq("activity_id", activityId));
    }

    /**
     * 查询活动数量
     */
    @Override
    public Integer queryCount() {
        // 获取我的id
        Long userId = UserContext.getUserId();
        // 查询活动数量
        return activityRelationMapper.selectCount(new QueryWrapper<ActivityRelation>().eq("user_id", userId));
    }

    /**
     * 查询该活动（群聊）下所有用户的id
     */
    @Override
    public List<Long> getUserIdsByActivityId(Long activityId) {
        // 参数校验
        if(activityId <= 0) {
            log.error("查询该活动（群聊）下所有用户的id -----> 参数错误");
            throw new BusinessExceptionHandler(CodeEnum.BAD_REQUEST.getCode());
        }

        // 进行数据库查询
        List<ActivityRelation> activityRelations = activityRelationMapper.selectList(new QueryWrapper<ActivityRelation>()
                .select("user_id")
                .eq("activity_id", activityId));
        return activityRelations.stream().map(ActivityRelation::getUserId).collect(Collectors.toList());
    }

    /**
     * 校验是根据自己的id还是其他人的id进行操作
     */
    private Long checkUserId(Long userId) {
        return userId == null ? UserContext.getUserId() : userId;
    }
}




