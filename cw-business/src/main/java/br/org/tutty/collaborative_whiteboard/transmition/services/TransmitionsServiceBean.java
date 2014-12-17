package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.cw.context.UserContext;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.cw.model.User;
import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;
import br.org.tutty.collaborative_whiteboard.transmition.model.Message;
import br.org.tutty.collaborative_whiteboard.transmition.model.OfflineMessage;
import br.org.tutty.collaborative_whiteboard.transmition.model.OnlineMessage;
import br.org.tutty.collaborative_whiteboard.transmition.model.UserMessage;
import org.json.JSONException;
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
public class TransmitionsServiceBean implements TransmitionsService, Serializable {

    @Inject
    private UserContext userContext;

    @Override
    public void connect(Session webSocketSession, HttpSession httpSession) {
        try {
            LoggedUser loggedUser = userContext.fetch(httpSession);

            // TODO refatorar para uso de multiplas conex WEBSOCKET
            if (!loggedUser.isActivityTransmition()) {
                loggedUser.activateTransmition(webSocketSession);
            }

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
            String userName = loggedUser.getUser().getName();

            UserMessage userMessage = new UserMessage(userName, messageData);
            broadcast(userMessage, webSocketSessionSender);

        } catch (DataNotFoundException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage, webSocketSessionSender);
        }
    }

    @Override
    public void disconect(Session webSocketSession) {
        try {
            LoggedUser loggedUser = userContext.fetch(webSocketSession);
            loggedUser.disableTransmition();

        } catch (DataNotFoundException e) {
        }

        OfflineMessage offlineMessage = new OfflineMessage();
        sendMessage(offlineMessage, webSocketSession);
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