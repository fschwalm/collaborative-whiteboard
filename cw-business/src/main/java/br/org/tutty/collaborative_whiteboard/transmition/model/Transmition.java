package br.org.tutty.collaborative_whiteboard.transmition.model;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by drferreira on 18/12/14.
 */
public class Transmition {
    private String transmitionCode;
    private List<Connection> connections;

    public Transmition() {
        this.connections = new ArrayList<>();
        this.transmitionCode = generateTransmitionCode();
    }

    public Boolean isParticipating(HttpSession httpSession) {
        Predicate<Connection> predicate = con -> con.getHttpSession().equals(httpSession.getId());

        try {
            connections.stream().filter(predicate).findFirst().get();
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public Connection fetchConnection(HttpSession httpSession) {
        return connections.stream()
                .filter(connection -> connection.getHttpSession().equals(httpSession.getId()))
                .findFirst().get();
    }

    public String getTransmitionCode() {
        return transmitionCode;
    }

    public Boolean getIn(Connection connection) {
        return connections.add(connection);
    }

    public Boolean getOut(Connection connection) {
        return connections.remove(connection);
    }

    private String generateTransmitionCode() {
        return UUID.randomUUID().toString();
    }
}
