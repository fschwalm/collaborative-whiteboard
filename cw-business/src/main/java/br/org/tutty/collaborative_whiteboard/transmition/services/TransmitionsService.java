package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.services.GlobalService;
import br.org.tutty.collaborative_whiteboard.transmition.MessageBuilder;
import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Created by drferreira on 27/11/14.
 */
public interface TransmitionsService extends GlobalService {

    public void connect(Session session, HttpSession httpSession);

    public void disconect(Session session);

    public void broadcast(MessageBuilder messageBuilder, Session session) throws MessageMountException;
}
