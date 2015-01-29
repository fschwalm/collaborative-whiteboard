package transmition;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by drferreira on 18/12/14.
 */
public class Transmition {
    private String transmitionCode;
    private List<Connection> connections;

    public Transmition(String transmitionCode) {
        this.transmitionCode = transmitionCode;
        this.connections = new ArrayList<>();
    }

    public Boolean isParticipating(HttpSession httpSession) {
        Predicate<Connection> predicate = con -> con.getHttpSessionId().equals(httpSession.getId());

        try {
            connections.stream().filter(predicate).findFirst().get();
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public Boolean isParticipating(Session socketSession) {
        Predicate<Connection> predicate = con -> con.getSocketSession().getId().equals(socketSession.getId());

        try {
            connections.stream().filter(predicate).findFirst().get();
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public Connection fetchConnection(Session socketSession) {
        return connections.stream()
                .filter(connection -> connection.getSocketSession().getId().equals(socketSession.getId()))
                .findFirst().get();
    }

    public Boolean getIn(Connection connection) {
        return connections.add(connection);
    }

    public Boolean getOut(Connection connection) {
        return connections.remove(connection);
    }

    public List<Connection> getAllConnections(){
        return connections;
    }

}
