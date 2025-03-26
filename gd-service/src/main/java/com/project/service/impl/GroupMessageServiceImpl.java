package com.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.GroupMessage;
import com.project.dto.message.GroupMessageDTO;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.GroupMessageMapper;
import com.project.service.GroupMessageService;
import com.project.vo.message.QueryGroupMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class GroupMessageServiceImpl extends ServiceImpl<GroupMessageMapper, GroupMessage>
        implements GroupMessageService {
    @Resource
    private GroupMessageMapper groupMessageMapper;

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
            return groupMessageMapper.queryGroupMessage(activityId);
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
}
