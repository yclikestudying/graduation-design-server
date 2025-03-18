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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

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
        return activityMapper.selectCount(new QueryWrapper<Activity>()
                .select("user_id")
                .eq("id", activityId));
    }
}
