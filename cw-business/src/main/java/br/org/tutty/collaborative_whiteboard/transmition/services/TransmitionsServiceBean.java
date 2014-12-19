package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.cw.context.UserContext;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.transmition.context.TransmitionContext;
import br.org.tutty.collaborative_whiteboard.transmition.model.*;
import br.org.tutty.collaborative_whiteboard.transmition.model.messages.Message;
import br.org.tutty.collaborative_whiteboard.transmition.model.messages.OfflineMessage;
import br.org.tutty.collaborative_whiteboard.transmition.model.messages.OnlineMessage;
import br.org.tutty.collaborative_whiteboard.transmition.model.messages.UserMessage;

import javax.ejb.Local;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;

/**
 * Created by drferreira on 27/11/14.
 */
@Local(TransmitionsService.class)
public class TransmitionsServiceBean implements TransmitionsService, Serializable {

    @Inject
    private UserContext userContext;

    @Inject
    private TransmitionContext transmitionContext;

    @Override
    public void connect(Session socketSessionSender, HttpSession httpSession) {
        try {
            transmitionContext.start("CODE", httpSession, socketSessionSender);

            OnlineMessage onlineMessage = new OnlineMessage();
            sendMessage(onlineMessage, socketSessionSender);

        } catch (ConnectException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage, socketSessionSender);
        }
    }

    @Override
    public void send(String messageData, Session socketSessionSender) {
        try {
            Transmition transmition = transmitionContext.fetch(socketSessionSender);
            Connection connection = transmition.fetchConnection(socketSessionSender);
            LoggedUser loggedUser = userContext.fetch(connection.getHttpSessionId());
            UserMessage userMessage = new UserMessage(loggedUser.getUser().getName(), messageData);

            broadcast(userMessage, socketSessionSender);

        } catch (DataNotFoundException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage, socketSessionSender);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void disconect(Session socketSession) {
        transmitionContext.end(socketSession);

        OfflineMessage offlineMessage = new OfflineMessage();
        sendMessage(offlineMessage, socketSession);
    }

    private void broadcast(Message message, Session webSocketSessionSender) {
        webSocketSessionSender.getOpenSessions().stream()
                .forEach(s -> sendMessage(message, s));
    }

    private void sendMessage(Message message, Session target) {
        try {
            target.getBasicRemote().sendText(message.toJSon().toString());
        } catch (IOException e) {
        }
    }
}