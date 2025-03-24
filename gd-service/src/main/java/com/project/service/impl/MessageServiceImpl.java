package com.project.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Message;
import com.project.dto.message.MessageDTO;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.MessageMapper;
import com.project.service.MessageService;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import com.project.vo.message.QueryGroupMessageVO;
import com.project.vo.message.QueryMessageVO;
import com.project.vo.message.QueryNoReadMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 消息已读
     */
    @Override
    public Boolean isRead(Long userId) {
        // 校验参数
        if (userId <= 0) {
            log.error("消息已读 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 获取自己的id
        Long myId = UserContext.getUserId();
        // 查询是否有未读消息
        Integer count = messageMapper.selectCount(new QueryWrapper<Message>()
                .eq("send_user_id", userId)
                .eq("accept_user_id", myId));
        if (count == 0) {
            return false;
        }
        // 把未读消息设置成已读消息
        return this.update(new UpdateWrapper<Message>()
                .eq("send_user_id", userId)
                .eq("accept_user_id", myId)
                .set("is_read", 1));
    }

    /**
     * 查询最新消息列表
     */
    @Override
    public List<QueryNoReadMessageVO> queryNoReadList() {
        // 获取自己的id
        Long userId = UserContext.getUserId();
        // 查询出给我发过消息的用户的id
        List<Message> messages = messageMapper.selectList(new QueryWrapper<Message>()
                .select("send_user_id")
                .eq("accept_user_id", userId));
        List<Long> idList = messages.stream().map(Message::getSendUserId).collect(Collectors.toList());
        // 根据 idList 和 userId 查询最新的一条消息集合
        List<QueryNoReadMessageVO> queryNoReadMessageVOS = new ArrayList<>();
        idList.forEach(id -> {
            // 每个与我聊过天的用户的最新一条消息
            queryNoReadMessageVOS.add(messageMapper.queryNoReadMessage(id, userId));
        });
        // 根据最新消息集合中的发送者的id和我的id查询我的未读消息数量
        if (queryNoReadMessageVOS.isEmpty()) {
            return null;
        }
        return queryNoReadMessageVOS.stream().peek(queryNoReadMessageVO -> {
            if (!Objects.equals(queryNoReadMessageVO.getSendUserId(), userId)) {
                Integer count = messageMapper.selectCount(new QueryWrapper<Message>()
                        .eq("send_user_id", queryNoReadMessageVO.getSendUserId())
                        .eq("accept_user_id", userId)
                        .eq("is_read", 0));
                queryNoReadMessageVO.setNoReadMessageCount(count);
            }
        }).sorted((vo1, vo2) -> {
            // 按时间降序排序
            return vo2.getCreateTime().compareTo(vo1.getCreateTime());
        }).collect(Collectors.toList());
    }

    /**
     * 查询未读消息总数
     */
    @Override
    public Integer queryNoReadTotal() {
        // 获取自己的id
        Long userId = UserContext.getUserId();
        // 查询总的未读消息
        return messageMapper.selectCount(new QueryWrapper<Message>()
                .eq("accept_user_id", userId)
                .eq("is_read", 0));
    }

    /**
     * 上传图片
     */
    @Override
    public String uploadImage(MultipartFile file) {
        // 上传图片
        try {
            return Upload.uploadAvatar(file, "message");
        } catch (IOException e) {
            log.error("上传图片 -----> 上传阿里云失败");
            throw new RuntimeException(e);
        }
    }

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
            return messageMapper.queryGroupMessage(activityId);
        } catch (Exception e) {
            log.error("查询群聊消息 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }
}




