package com.websocket.socketcomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.netty.util.internal.ConcurrentSet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liwen
 * @Title: Mywebsocket
 * @Description: 服务端
 * @date 2019/12/9 / 13:34
 */
@ServerEndpoint("/chatWebsocket")
@Component
public class Mywebsocket {
    private static final Logger logger = LoggerFactory.getLogger(Mywebsocket.class);

    /**
     * 当前在线连接数
     */
    private static AtomicInteger onlineSize = new AtomicInteger(0);

    private static Set<Mywebsocket> webSocketSet = new ConcurrentSet<Mywebsocket>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) {

        this.session = session;
        webSocketSet.add(this);
        addOnlineSize();
        logger.info("有客户端连接 当前在线人数:" + getOnlineSize());
        try {
            sendMessage("你已成功连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        logger.info("关闭");
        webSocketSet.remove(this);
        subOnlineSize();
        logger.info("有客户端关闭连接，当前在线人数为:" + getOnlineSize());
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        logger.info("接收客户端消息 :" + message);
        if (session.isOpen()) {
            //群发消息
            for (Mywebsocket item : webSocketSet) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //群发
                synchronized (session) {
                    item.sendInfoToAll(message);
                }
            }
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        if (session.isOpen()) {
            this.session.getAsyncRemote().sendText(message);
        }
    }

    public void sendInfoToAll(String message) {

        webSocketSet.parallelStream().forEach(item -> {
            try {
                if (session.isOpen()) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info("群发消息 " + message);
        });

    }

    public int getOnlineSize() {
        return onlineSize.get();
    }

    public void addOnlineSize() {
        onlineSize.addAndGet(1);
    }

    public void subOnlineSize() {
        onlineSize.addAndGet(-1);
    }


}
