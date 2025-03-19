package com.project.websocket;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/wsConnect/{userId}")
@Component
@Slf4j
public class WebSocketServer {
    // 存放每个客户端对应的 websocket 对象
    private static final ConcurrentHashMap<Session, Long> webSocketMap = new ConcurrentHashMap<>();
    private static ApplicationContext applicationContext;
    private final Gson gson = new Gson();

    // 客户端与服务器建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        if (!webSocketMap.containsKey(session)) {
            webSocketMap.put(session, userId);
        }
        log.info("用户:{} 成功建立连接", userId);
    }

    // 客户端发送消息
    @OnMessage
    public void onMessage(String chatContent) throws IOException {

    }

    // 客户端关闭连接
    @OnClose
    public void onClose(Session session) {
        if (webSocketMap.containsKey(session)) {
            log.info("用户:{} 成功退出连接", webSocketMap.get(session));
            webSocketMap.remove(session);
        }
    }

    // 一对一聊天
    public void sendMessage(Long userId, String chatContent) throws IOException {
        for (Session session : webSocketMap.keySet()) {
            if (webSocketMap.get(session).equals(userId)) {
                session.getBasicRemote().sendText(chatContent);
            }
        }
    }

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
}
