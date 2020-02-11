package de.ing.easyfinancing.creditApplication.web;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/StatusUpdate/{id}")
public class CreditApplicationWebsocket {
    @OnOpen
    public void open(Session session) {
    }

    @OnClose
    public void close(Session session) {
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {

    }
}
