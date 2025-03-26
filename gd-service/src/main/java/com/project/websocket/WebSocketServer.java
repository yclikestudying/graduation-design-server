package com.project.websocket;

import com.google.gson.Gson;
import com.project.constant.WebSocketConstant;
import com.project.dto.message.GroupMessageDTO;
import com.project.dto.message.MessageDTO;
import com.project.dto.ws.WebSocketDTO;
import com.project.service.ActivityRelationService;
import com.project.service.GroupMessageService;
import com.project.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ServerEndpoint("/wsConnect/{userId}")
@Component
@Slf4j
public class WebSocketServer {
    // 存放每个客户端对应的 websocket 对象
    private static final ConcurrentHashMap<Long, Session> webSocketMap = new ConcurrentHashMap<>();
    private static ApplicationContext applicationContext;
    private final Gson gson = new Gson();
    @Resource
    private ThreadPoolTaskExecutor executor;

    // 客户端与服务器建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        if (!webSocketMap.containsKey(userId)) {
            webSocketMap.put(userId, session);
            log.info("id为 {} 的用户建立连接成功", userId);
        } else {
            log.warn("id为 {} 的用户正在重复连接", userId);
        }
    }

    // 客户端发送消息
    @OnMessage
    public void onMessage(String data) throws IOException {
        WebSocketDTO webSocketDTO = gson.fromJson(data, WebSocketDTO.class);
        // 获取类型
        String type = webSocketDTO.getType();
        if (WebSocketConstant.DIRECT_MESSAGE.equals(type)) {
            // 接收私聊消息
            directMessage(gson.fromJson(webSocketDTO.getData(), MessageDTO.class), type);
        } else if(WebSocketConstant.GROUP_MESSAGE.equals(type)) {
            groupMessage(gson.fromJson(webSocketDTO.getData(), GroupMessageDTO.class), type);
        }
    }

    // 客户端关闭连接
    @OnClose
    public void onClose(Session session) {
        Set<Map.Entry<Long, Session>> entries = webSocketMap.entrySet();
        for (Map.Entry<Long, Session> entry : entries) {
            if (entry.getValue() == session) {
                webSocketMap.remove(entry.getKey());
                log.info("id为 {} 的用户断开连接", entry.getKey());
            }
        }
    }

    // 客户端发生错误
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("WebSocket 发生错误:", throwable);
        onClose(session);
    }

    // 接收私聊消息
    public void directMessage(MessageDTO messageDTO, String type) {
        MessageService messageService = applicationContext.getBean(MessageService.class);
        boolean result = messageService.insertDirectMessage(messageDTO);
        if (result) {
            Map<String, String> map = new HashMap<>();
            map.put("type", type);
            map.put("data", gson.toJson(messageDTO));
            // 进行前端消息广播
            messageBroadcast(messageDTO.getSendUserId(), map);
            messageBroadcast(messageDTO.getAcceptUserId(), map);
        }
    }

    // 接收群聊消息
    public void groupMessage(GroupMessageDTO groupMessageDTO, String type) {
        GroupMessageService groupMessageService = applicationContext.getBean(GroupMessageService.class);
        boolean result = groupMessageService.insertGroupMessage(groupMessageDTO);
        if (result) {
            // 群消息存储成功，获取该群id下的所有用户，然后进行消息推送
            Long activityId = groupMessageDTO.getActivityId();
            ActivityRelationService activityRelationService = applicationContext.getBean(ActivityRelationService.class);
            List<Long> idList = activityRelationService.getUserIdsByActivityId(activityId);
            // 循环推送
            idList.forEach(id -> {
                Map<String, String> map = new HashMap<>();
                map.put("type", type);
                map.put("data", gson.toJson(groupMessageDTO));
                messageBroadcast(id, map);
            });
        }
    }

    // 一对一消息广播
    public void messageBroadcast(Long userId, Map<String, String> map) {
        Session session = webSocketMap.get(userId);
        try {
            if (session != null) {
                session.getBasicRemote().sendText(gson.toJson(map));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
}
