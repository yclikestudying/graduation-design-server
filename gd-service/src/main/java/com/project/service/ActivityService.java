package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Activity;
import org.springframework.web.multipart.MultipartFile;

public interface ActivityService extends IService<Activity> {
    /**
     * 创建活动
     */
    boolean uplaod(MultipartFile file, String title, String description, Integer max);
    /**
     * 查询活动最大人数
     */
    Integer activityMaxPeople(Long activityId);
}
