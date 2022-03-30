package edu.wfit.liveteaching.controller;


import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@ServerEndpoint("/websocket")
public class liveCount {
    private static int online_num=0;
    private static CopyOnWriteArraySet<liveCount> webSocketSet=new CopyOnWriteArraySet<liveCount>();


    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有链接加入"+getOnline_num());

    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有连接关闭，当前人数为"+getOnline_num());
    }

    private synchronized int getOnline_num() {
        return liveCount.online_num;
    }

    private synchronized int  addOnlineCount() {
        return liveCount.online_num--;
    }



    private synchronized int subOnlineCount() {
        return liveCount.online_num;
    }



}
