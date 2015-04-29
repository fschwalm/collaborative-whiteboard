package br.org.tutty.collaborative_whiteboard.transmition.services;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by drferreira on 27/11/14.
 */
public interface TransmitionsService{

    public void connect(Session session, HttpSession httpSession);

    void send(String messageData, Session webSocketSessionSender);

    public void disconect(Session session) throws IOException;
}
