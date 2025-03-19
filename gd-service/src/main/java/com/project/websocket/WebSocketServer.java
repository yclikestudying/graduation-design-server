package com.project.websocket;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/wsConnect/{userId}")
@Component
@Slf4j
public class WebSocketServer {
    // 存放每个客户端对应的 websocket 对象
    private static final ConcurrentHashMap<Long, Session> webSocketMap = new ConcurrentHashMap<>();
    private static ApplicationContext applicationContext;
    private final Gson gson = new Gson();

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
    public void onMessage(String chatContent) throws IOException {

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

    // 一对一聊天
    public void sendMessage(Long userId, String chatContent) throws IOException {

    }

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
}
