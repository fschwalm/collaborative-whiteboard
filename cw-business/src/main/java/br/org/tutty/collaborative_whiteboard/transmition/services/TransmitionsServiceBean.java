package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.model.User;
import br.org.tutty.collaborative_whiteboard.services.UserService;
import br.org.tutty.collaborative_whiteboard.services.GlobalServiceBean;
import br.org.tutty.collaborative_whiteboard.transmition.MessageBuilder;
import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;
import org.json.JSONObject;

import javax.ejb.Local;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by drferreira on 27/11/14.
 */
@Local(TransmitionsService.class)
public class TransmitionsServiceBean implements TransmitionsService, Serializable{

    @Inject
    public UserService userService;

    @Override
    public void connect(Session session, HttpSession httpSession) {
        userService.authorizedUser(httpSession, session);
    }

    @Override
    public void disconect(Session session) {
        userService.getLoggedUser()
        userService.unauthorizedUser();
    }

    public void broadcast(MessageBuilder messageBuilder, Session session) throws MessageMountException{
        JSONObject message = messageBuilder.getMessage();

        session.getOpenSessions().forEach(s -> {
            if (!s.getId().equals(session.getId()))
                try {
                    s.getBasicRemote().sendText(message.toString());
                } catch (IOException e) {
                }
        });
    }
}