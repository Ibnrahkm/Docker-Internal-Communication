package com.example.server;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class WebSocketHandler extends TextWebSocketHandler {

    Map<String,WebSocketSession>sessionMap=new HashMap<String,WebSocketSession>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String mgs = message.getPayload();
        System.out.println(mgs);
        for (Map.Entry<String,WebSocketSession> webSocketSessionEntry:sessionMap.entrySet()){
            webSocketSessionEntry.getValue().sendMessage(new TextMessage("received:: " + mgs));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getId(),session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session.getId(),session);
        super.afterConnectionEstablished(session);
    }
}
