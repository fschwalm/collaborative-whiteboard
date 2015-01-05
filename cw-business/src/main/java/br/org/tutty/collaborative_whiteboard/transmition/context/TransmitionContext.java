package br.org.tutty.collaborative_whiteboard.transmition.context;

import br.org.tutty.collaborative_whiteboard.transmition.model.Connection;
import br.org.tutty.collaborative_whiteboard.transmition.model.Transmition;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 18/12/14.
 */
@ApplicationScoped
public class TransmitionContext implements Serializable {
    private List<Transmition> transmitions;

    @PostConstruct
    public void setUp() {
        this.transmitions = new ArrayList<>();
    }

    public void start(String transmitionCode, HttpSession httpSession, Session socketSession) throws ConnectException {
        try{
            Connection connection = new Connection(socketSession, httpSession);

            if(isActive(transmitionCode)){
                signInExistingTransmission(socketSession, connection);
            }else {
                signInNewTransmission(transmitionCode, connection);
            }
        }catch (Exception e){
            throw new ConnectException();
        }
    }

    public Boolean end(Session socketSession) {
        List<Connection> allConnections = fetch(socketSession).getAllConnections();
        return allConnections
                .removeIf(connection -> connection.getSocketSession().getId().equals(socketSession.getId()));
    }

    public Boolean isActive(String transmitionCode) {
        return transmitions.stream()
                .anyMatch(transmition -> transmition.getTransmitionCode().equals(transmitionCode));
    }

    public Transmition fetch(Session socketSession) {
        return transmitions.stream()
                .filter(transmition -> transmition.isParticipating(socketSession))
                .findFirst()
                .get();
    }

    private void signInExistingTransmission(Session socketSession, Connection connection){
        Transmition transmition = fetch(socketSession);
        transmition.signIn(connection);
    }

    private void signInNewTransmission(String transmitionCode, Connection connection){
        Transmition transmition = new Transmition(transmitionCode);
        transmition.signIn(connection);
        transmitions.add(transmition);
    }
}
