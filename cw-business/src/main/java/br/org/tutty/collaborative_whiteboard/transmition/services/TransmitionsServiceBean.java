package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.cw.context.UserGlobalContext;
import br.org.tutty.collaborative_whiteboard.transmition.context.TransmitionContext;
import cw.dtos.LoggedUser;
import cw.exceptions.DataNotFoundException;
import transmition.Connection;
import transmition.Transmition;
import transmition.messages.Message;
import transmition.messages.OfflineMessage;
import transmition.messages.OnlineMessage;
import transmition.messages.UserMessage;

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
    private UserGlobalContext userGlobalContext;

    @Inject
    private TransmitionContext transmitionContext;

    @Override
    public void connect(Session socketSessionSender, HttpSession httpSession) {
        try {
            LoggedUser loggedUser = userGlobalContext.fetch(httpSession.getId());
            transmitionContext.start(loggedUser, httpSession, socketSessionSender);

            OnlineMessage onlineMessage = new OnlineMessage();
            sendMessage(onlineMessage, socketSessionSender);

        } catch (ConnectException | DataNotFoundException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage, socketSessionSender);
        }
    }

    @Override
    public void send(String messageData, Session socketSessionSender) {
        try {
            Transmition transmition = transmitionContext.fetch(socketSessionSender);
            Connection connection = transmition.fetchConnection(socketSessionSender);
            LoggedUser loggedUser = userGlobalContext.fetch(connection.getHttpSessionId());
            UserMessage userMessage = new UserMessage(loggedUser.getUser().getFirstName(), messageData);

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