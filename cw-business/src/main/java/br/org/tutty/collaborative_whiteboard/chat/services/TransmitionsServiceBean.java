package br.org.tutty.collaborative_whiteboard.chat.services;

import br.org.tutty.collaborative_whiteboard.cw.context.UserGlobalContext;
import br.org.tutty.collaborative_whiteboard.chat.context.TransmitionContext;
import br.org.tutty.transmition.TransmitionDao;
import cw.dtos.LoggedUser;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import transmition.Connection;
import transmition.Transmition;
import transmition.entities.SentMessage;
import transmition.messages.*;

import javax.ejb.Local;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 27/11/14.
 */
@Local(TransmitionsService.class)
public class TransmitionsServiceBean implements TransmitionsService, Serializable {

    private final static Integer DEFAULT_SIZE_LAST_MESSAGES = 100;

    @Inject
    private UserGlobalContext userGlobalContext;

    @Inject
    private TransmitionContext transmitionContext;

    @Inject
    private TransmitionDao transmitionDao;

    @Override
    public void connect(Session socketSessionSender, HttpSession httpSession) {
        try {
            LoggedUser loggedUser = userGlobalContext.fetch(httpSession.getId());
            transmitionContext.start(loggedUser, httpSession, socketSessionSender);

            List<SentMessage> lastMessages = fetchLastMessages();

            OnlineMessage onlineMessage = new OnlineMessage(lastMessages);
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
            User user = loggedUser.getUser();
            UserMessage userMessage = new UserMessage(user.getFirstName(), messageData);

            saveMessage(user, userMessage);
            broadcast(userMessage, socketSessionSender);

        } catch (DataNotFoundException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage, socketSessionSender);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void disconect(Session socketSession) throws IOException {
        OfflineMessage offlineMessage = new OfflineMessage();
        broadcast(offlineMessage, socketSession);
        transmitionContext.end(socketSession);
    }

    public List<SentMessage> fetchLastMessages(){
        return transmitionDao.fetchLastMessages(DEFAULT_SIZE_LAST_MESSAGES);
    }


    private void broadcast(Message message, Session webSocketSessionSender) {
        webSocketSessionSender.getOpenSessions().stream()
                .forEach(s -> sendMessage(message, s));
    }

    private void sendMessage(Message message, Session sessionTarget) {
        try {
            sessionTarget.getBasicRemote().sendText(message.toJSon().toString());
        } catch (IOException e) {
        }
    }

    private void saveMessage(User sender, UserMessage message){
        Date date = message.getDate();
        String messageData = message.getMessage();
        SentMessage sentMessage = new SentMessage(sender, date, TypeMessage.USER_MESSAGE, messageData);
        transmitionDao.persist(sentMessage);
    }
}