package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.cw.context.UserContext;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.transmition.context.TransmitionContext;
import br.org.tutty.collaborative_whiteboard.transmition.model.*;

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
public class TransmitionsServiceBean implements TransmitionsService, Serializable {

    @Inject
    private UserContext userContext;

    @Inject
    private TransmitionContext transmitionContext;

    @Override
    public void connect(Session webSocketSession, HttpSession httpSession) {
        try {
            LoggedUser loggedUser = userContext.fetch(httpSession);

            // TODO ATRELAR ID TRANSMISSAO COM ID USUARIO

            transmitionContext.start(httpSession, webSocketSession);

            OnlineMessage onlineMessage = new OnlineMessage();
            sendMessage(onlineMessage, webSocketSession);

        } catch (DataNotFoundException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage, webSocketSession);
        }
    }

    @Override
    public void send(String messageData, Session webSocketSessionSender) {
        try {
        LoggedUser loggedUser = userContext.fetch(webSocketSessionSender);


            // TODO CONTINUAR MECANISMO DE ENVIO

        } catch (DataNotFoundException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage, webSocketSessionSender);
        }
    }

    @Override
    public void disconect(Session socketSession) {
        try {
            transmitionContext.end(socketSession);

        } catch (DataNotFoundException e) {
        }

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