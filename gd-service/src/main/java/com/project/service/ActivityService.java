package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Activity;
import com.project.vo.activity.QueryActivityVO;
import com.project.vo.activity.QueryOneActivityVO;
import com.project.vo.activityRelation.ActivityRelationVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 获取群聊名称和人数
     */
    ActivityRelationVO queryNameAndCount(Long activityId);

    /**
     * 查询用户所参见的活动（包括自己创建的）
     */
    List<QueryActivityVO> queryJoinedActivity();

    /**
     * 分页查询群聊
     */
    Map<String, Object> queryGroupChatByPage(Integer current, Integer size);

    /**
     * 批量删除群聊
     */
    boolean deleteActivityBatch(List<Long> activityIdList);

    /**
     * 按时间搜索群聊
     */
    Map<String, Object> queryActivityByTime(String time, Integer current, Integer size);

    /**
     * 查询用户所创建以及所参加的所有群聊的id
     */
    Set<Long> queryActivityIdList(Long userId);

    /**
     * 根据群id查询群名称和群头像
     */
    Map<String, String> queryActivityNameAndPhoto(Long activityId);

    /**
     * 根据群id查询群创建用户id
     */
    Long queryUserId(Long activityId);
}
