package br.org.tutty.collaborative_whiteboard.transmition.services;

import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Created by drferreira on 27/11/14.
 */
public interface TransmitionsService{

    public void connect(Session session, HttpSession httpSession);

    public void disconect(Session session);

    public void broadcast(String dataMessage, Session webSocketSessionSender);
}
