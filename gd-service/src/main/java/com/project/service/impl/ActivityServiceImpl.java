package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.project.vo.activityRelation.ActivityRelationVO;
import com.project.vo.goods.QueryGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
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
     * 获取群聊名称和人数
     */
    @Override
    public ActivityRelationVO queryNameAndCount(Long activityId) {
        // 校验参数
        if (activityId <= 0) {
            log.error("获取群聊名称和人数 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 查询数据库
        try {
            return activityMapper.queryNameAndCount(activityId);
        } catch (Exception e) {
            log.error("获取群聊名称和人数 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询用户所参见的活动（包括自己创建的）
     */
    @Override
    public List<QueryActivityVO> queryJoinedActivity() {
        // 获取我的id
        Long userId = UserContext.getUserId();
        // 查询我所参加的活动
        List<QueryActivityVO> queryActivityVOS = activityMapper.queryJoinedActivity(userId);
        return queryActivityVOS.stream().peek(queryActivityVO -> {
            Integer count = activityRelationService.queryCount(queryActivityVO.getId());
            queryActivityVO.setCurrentPeople(count);
        }).collect(Collectors.toList());
    }

    /**
     * 分页查询群聊
     */
    @Override
    public Map<String, Object> queryGroupChatByPage(Integer current, Integer size) {
        // 校验参数
        if (current <= 0 || size <= 0) {
            log.error("分页查询群聊 -----> 分页参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 进行查询
        Page<QueryActivityVO> page = new Page<>(current, size);
        Page<QueryActivityVO> queryActivityVOPage = activityMapper.queryGroupChatByPage(page);
        List<QueryActivityVO> records = queryActivityVOPage.getRecords().stream().peek(queryActivityVO -> {
            Integer count = activityRelationService.queryCount(queryActivityVO.getId());
            queryActivityVO.setCurrentPeople(count);
        }).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("groupChat", records);
        map.put("total", queryActivityVOPage.getTotal());
        return map;
    }

    /**
     * 批量删除群聊
     */
    @Override
    public boolean deleteActivityBatch(List<Long> activityIdList) {
        // 参数校验
        if (activityIdList.isEmpty()) {
            log.error("批量删除群聊 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 执行操作
        return activityMapper.deleteBatchIds(activityIdList) > 0;
    }

    /**
     * 按时间搜索群聊
     */
    @Override
    public Map<String, Object> queryActivityByTime(String time, Integer current, Integer size) {
        // 校验参数
        if (StringUtils.isBlank(time ) || current <= 0 || size <= 0) {
            log.error("按时间搜索群聊 -----> 分页参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 进行查询
        Page<QueryActivityVO> page = new Page<>(current, size);
        Page<QueryActivityVO> queryActivityVOPage = activityMapper.queryActivityByTime(page, time);
        List<QueryActivityVO> records = queryActivityVOPage.getRecords().stream().peek(queryActivityVO -> {
            Integer count = activityRelationService.queryCount(queryActivityVO.getId());
            queryActivityVO.setCurrentPeople(count);
        }).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("groupChat", records);
        map.put("total", queryActivityVOPage.getTotal());
        return map;
    }

    /**
     * 查询用户所创建以及所参加的所有群聊的id
     */
    @Override
    public Set<Long> queryActivityIdList(Long userId) {
        // 查询我所创建的群聊id
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        List<Activity> activityList = activityMapper.selectList(queryWrapper.select("id").eq("user_id", userId));
        Set<Long> createActivityIdList = activityList.stream().map(Activity::getId).collect(Collectors.toSet());

        // 查询我所参加的群聊id
        Set<Long> addActivityIdList = activityRelationService.queryActivityIdList(userId);

        // 合并
        Set<Long> activityIdList = new HashSet<>();
        activityIdList.addAll(createActivityIdList);
        activityIdList.addAll(addActivityIdList);

        return activityIdList;
    }

    /**
     * 根据群id查询群名称和群头像
     */
    @Override
    public Map<String, String> queryActivityNameAndPhoto(Long activityId) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        Activity activity = activityMapper.selectOne(queryWrapper.select("activity_name", "activity_photo").eq("id", activityId));
        Map<String, String> map = new HashMap<>();
        map.put("activityName", activity.getActivityName());
        map.put("activityPhoto", activity.getActivityPhoto());
        return map;
    }

    /**
     * 根据群id查询群创建用户id
     */
    @Override
    public Long queryUserId(Long activityId) {
        Activity activity = activityMapper.selectOne(new QueryWrapper<Activity>().select("user_id").eq("id", activityId));
        return activity.getUserId();
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
