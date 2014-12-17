package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.transmition.model.Message;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Created by drferreira on 27/11/14.
 */
public interface TransmitionsService{

    public void connect(Session session, HttpSession httpSession);

    void send(String messageData, Session webSocketSessionSender);

    public void disconect(Session session);
}
