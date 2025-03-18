package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Activity;
import com.project.domain.Lost;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.ActivityMapper;
import com.project.service.ActivityRelationService;
import com.project.service.ActivityService;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import com.project.vo.activity.QueryActivityVO;
import com.project.vo.activity.QueryOneActivityVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private ActivityRelationService activityRelationService;

    /**
     * 创建活动
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean uplaod(MultipartFile file, String title, String description, Integer max) {
        // 校验参数
        if (StringUtils.isAnyBlank(title, description)) {
            log.error("创建活动 -----> 参数不能为空");
            throw new BusinessExceptionHandler(400, "参数不能为空");
        }
        if (max < 2) {
            log.error("创建活动 -----> 最大人数不能小于2");
            throw new BusinessExceptionHandler(400, "最大人数不能小于2");
        }
        // 上传阿里云
        String link = null;
        try {
            link = Upload.uploadAvatar(file, "activity");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Activity activity = new Activity();
        activity.setUserId(UserContext.getUserId());
        activity.setActivityPhoto(link);
        activity.setActivityName(title);
        activity.setActivityDescription(description);
        activity.setActivityMaxPeople(max);
        try {
            int insert = activityMapper.insert(activity);
            if (insert > 0) {
                activityRelationService.addActivity(activity.getId());
            }
            return true;
        } catch (Exception e) {
            log.error("创建活动 -----> 数据插入数据库错误");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询活动最大人数
     */
    @Override
    public Integer activityMaxPeople(Long activityId) {
        return activityMapper.selectOne(new QueryWrapper<Activity>()
                .select("activity_max_people")
                .eq("id", activityId)).getActivityMaxPeople();
    }

    /**
     * 查询用户创建的活动
     */
    @Override
    public List<QueryActivityVO> queryActivity(Long userId) {
        // 校验参数
        userId = userId == null ? UserContext.getUserId() : userId;
        // 查询用户创建的活动列表
        return queryActivity(userId, null);
    }

    /**
     * 查询所有活动
     */
    @Override
    public List<QueryActivityVO> queryAllActivity() {
        // 查询活动列表
        return queryActivity(null, null);
    }

    /**
     * 关键字模糊查询活动
     */
    @Override
    public List<QueryActivityVO> queryActivityByKeyword(String keyword) {
        // 查询活动列表
        return queryActivity(null, keyword);
    }

    /**
     * 根据id删除活动
     */
    @Override
    public boolean deleteActivity(Long activityId) {
        // 校验参数
        if (activityId <= 0) {
            log.error("根据id删除活动----->参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 删除数据库动态记录
        int result;
        try {
            result = activityMapper.deleteById(activityId);
        } catch (Exception e) {
            log.error("根据id删除活动----->数据库删除失败");
            throw new RuntimeException(e);
        }
        if (result == 0) {
            log.error("根据id删除活动 -----> 不存在");
            throw new BusinessExceptionHandler(400, "不存在");
        }
        return result > 0;
    }

    /**
     * 根据活动id查询活动
     */
    @Override
    public QueryOneActivityVO queryActivityById(Long activityId) {
        // 校验参数
        if (activityId <= 0) {
            log.error("根据活动id查询活动 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 联表查询活动以及发布者信息
        QueryOneActivityVO queryOneActivityVO = activityMapper.queryActivityById(activityId);
        // 根据活动id查询活动当前人数
        Integer count = activityRelationService.queryCount(queryOneActivityVO.getId());
        queryOneActivityVO.setCurrentPeople(count);
        return queryOneActivityVO;
    }

    /**
     * 用户查询
     * 全部查询
     * 模糊查询
     */
    private List<QueryActivityVO> queryActivity(Long userId, String keyword) {
        List<QueryActivityVO> queryActivityVOS = activityMapper.queryActivity(userId, keyword);
        // 根据每个活动查询活动的当前人数
        return queryActivityVOS.stream().peek(queryActivityVO -> {
            Integer count = activityRelationService.queryCount(queryActivityVO.getId());
            queryActivityVO.setCurrentPeople(count);
        }).collect(Collectors.toList());
    }
}
