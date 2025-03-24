package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Activity;
import com.project.vo.activity.QueryActivityVO;
import com.project.vo.activity.QueryOneActivityVO;
import com.project.vo.activityRelation.ActivityRelationVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActivityService extends IService<Activity> {
    /**
     * 创建活动
     */
    boolean uplaod(MultipartFile file, String title, String description, Integer max);
    /**
     * 查询活动最大人数
     */
    Integer activityMaxPeople(Long activityId);

    /**
     * 查询用户创建的活动
     */
    List<QueryActivityVO> queryActivity(Long userId);

    /**
     * 查询所有活动
     */
    List<QueryActivityVO> queryAllActivity();

    /**
     * 关键字模糊查询活动
     */
    List<QueryActivityVO> queryActivityByKeyword(String keyword);

    /**
     * 根据id删除活动
     */
    boolean deleteActivity(Long activityId);

    /**
     * 根据活动id查询活动
     */
    QueryOneActivityVO queryActivityById(Long activityId);

    /**
     * 查询活动数量
     */
    Integer queryCount();

    /**
     * 获取群聊名称和人数
     */
    ActivityRelationVO queryNameAndCount(Long activityId);
}
