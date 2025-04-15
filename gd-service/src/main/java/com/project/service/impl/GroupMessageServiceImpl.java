package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.GroupMessage;
import com.project.dto.message.GroupMessageDTO;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.GroupMessageMapper;
import com.project.service.ActivityService;
import com.project.service.GroupMessageService;
import com.project.service.UserService;
import com.project.utils.UserContext;
import com.project.vo.message.QueryGroupChatLatestMessageVO;
import com.project.vo.message.QueryGroupMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupMessageServiceImpl extends ServiceImpl<GroupMessageMapper, GroupMessage>
        implements GroupMessageService {
    @Resource
    private GroupMessageMapper groupMessageMapper;
    @Resource
    private ActivityService activityService;
    @Resource
    private UserService userService;

    /**
     * 查询群聊消息
     */
    @Override
    public List<QueryGroupMessageVO> queryGroupMessage(Long activityId) {
        // 校验参数
        if (activityId <= 0) {
            log.error("查询群聊消息 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 查询数据库记录
        try {
            List<QueryGroupMessageVO> messageVOList = groupMessageMapper.queryGroupMessage(activityId);

            // 查询该群聊的创建用户id
            Long userId = activityService.queryUserId(activityId);

            // 检查当前发送消息用户是否为群聊创建者
            return messageVOList.stream().peek(queryGroupMessageVO -> {
                if (Objects.equals(queryGroupMessageVO.getSendUserId(), userId)) {
                    queryGroupMessageVO.setUserName("群主");
                }
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询群聊消息 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 存储群消息
     */
    @Override
    public boolean insertGroupMessage(GroupMessageDTO groupMessageDTO) {
        // 获取所有参数
        Long activityId = groupMessageDTO.getActivityId();
        Long sendUserId = groupMessageDTO.getSendUserId();
        String messageType = groupMessageDTO.getMessageType();
        String messageContent = groupMessageDTO.getMessageContent();

        // 校验参数
        if (activityId <= 0 || sendUserId <= 0) {
            log.error("存储群消息 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        if (StringUtils.isAnyBlank(messageContent, messageType)) {
            log.error("存储群消息 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 保存至数据库
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setActivityId(activityId);
        groupMessage.setSendUserId(sendUserId);
        groupMessage.setMessageType(messageType);
        groupMessage.setMessageContent(messageContent);
        return groupMessageMapper.insert(groupMessage) > 0;
    }

    /**
     * 查询最新群聊消息列表
     */
    @Override
    public List<QueryGroupChatLatestMessageVO> queryGroupChatLatestMessage() {
        // 获取我的id
        Long userId = UserContext.getUserId();

        // 查询我所创建以及所参加的群聊id
        Set<Long> activityIdList = activityService.queryActivityIdList(userId);

        // 校验查询结果
        if (activityIdList.isEmpty()) {
            log.error("查询最新群聊消息列表 -----> 没有创建或参加任何群聊");
            throw new BusinessExceptionHandler(400, "没有创建或参加任何群聊");
        }

        // 根据每个群聊id查询最新一条聊天记录
        List<QueryGroupChatLatestMessageVO> list = new ArrayList<>();
        activityIdList.forEach(activityId -> {
            QueryGroupChatLatestMessageVO queryGroupChatLatestMessageVO = new QueryGroupChatLatestMessageVO();
            queryGroupChatLatestMessageVO.setActivityId(activityId);

            // 根据群id查询群聊名称以及群聊头像
            Map<String, String> map = activityService.queryActivityNameAndPhoto(activityId);
            queryGroupChatLatestMessageVO.setActivityName(map.get("activityName"));
            queryGroupChatLatestMessageVO.setActivityPhoto(map.get("activityPhoto"));

            // 根据群id查询最新一条消息（发送者名称、消息内容、消息类型、发送时间）
            GroupMessage groupMessage = groupMessageMapper.selectOne(new QueryWrapper<GroupMessage>()
                    .select("send_user_id", "message_content", "message_type", "send_time")
                    .eq("activity_id", activityId)
                    .orderByDesc("send_time")
                    .last("limit 1"));
            // 根据用户id查询用户名称
            queryGroupChatLatestMessageVO.setUserName(userService.getUserName(groupMessage.getSendUserId()));
            queryGroupChatLatestMessageVO.setSendUserId(groupMessage.getSendUserId());
            queryGroupChatLatestMessageVO.setMessageContent(groupMessage.getMessageContent());
            queryGroupChatLatestMessageVO.setMessageType(groupMessage.getMessageType());
            queryGroupChatLatestMessageVO.setSendTime(groupMessage.getSendTime());

            list.add(queryGroupChatLatestMessageVO);
        });

        // 根据最新一条消息的发送时间进行降序排序
        return list.stream().sorted((vo1, vo2) -> vo2.getSendTime().compareTo(vo1.getSendTime())).collect(Collectors.toList());
    }
}
