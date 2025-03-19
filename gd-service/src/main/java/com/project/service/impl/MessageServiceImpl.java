package com.project.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Message;
import com.project.dto.message.MessageDTO;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.MessageMapper;
import com.project.service.MessageService;
import com.project.utils.UserContext;
import com.project.vo.message.QueryMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    /**
     * 查询聊天信息
     */
    @Override
    public List<QueryMessageVO> queryMessage(Long userId) {
        // 校验对方用户id
        if (userId <= 0) {
            log.error("查询聊天信息 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 获取我的id
        Long myId = UserContext.getUserId();
        try {
            return messageMapper.queryMessage(userId, myId);
        } catch (Exception e) {
            log.error("查询聊天信息 -----> 查询数据库失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 存储私聊消息
     */
    @Override
    public boolean insertDirectMessage(MessageDTO messageDTO) {
        // 获取数据
        Long sendUserId = messageDTO.getSendUserId();
        Long acceptUserId = messageDTO.getAcceptUserId();
        String messageContent = messageDTO.getMessageContent();
        String messageType = messageDTO.getMessageType();
        // 校验参数
        if (sendUserId <= 0 || acceptUserId <= 0) {
            log.error("存储私聊消息 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        if (StringUtils.isAnyBlank(messageContent, messageType)) {
            log.error("存储私聊消息 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 保存数据库
        Message message = new Message();
        message.setSendUserId(sendUserId);
        message.setAcceptUserId(acceptUserId);
        message.setMessageContent(messageContent);
        message.setMessageType(messageType);
        try {
            return messageMapper.insert(message) > 0;
        } catch (Exception e) {
            log.error("存储私聊消息 -----> 数据库插入失败");
            throw new RuntimeException(e);
        }
    }
}




