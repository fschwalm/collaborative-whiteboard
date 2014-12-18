package br.org.tutty.collaborative_whiteboard.transmition.context;

import br.org.tutty.collaborative_whiteboard.transmition.model.Connection;
import br.org.tutty.collaborative_whiteboard.transmition.model.Transmition;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

    public void start(HttpSession httpSession, Session socketSession) {
        if(isTransmitting(httpSession)) {
            updateTransmition(httpSession, socketSession);
        }else {
            addTransmition(new Connection(socketSession, httpSession));
        }
    }

    public void end(Session socketSession) {
        // TODO BUSCAR QUAL TRANSMITION POSSUI O SOCKET DO USUARIO E REMOVER O SOCKET DA LISTA
    }

    public Boolean isTransmitting(HttpSession httpSession) {
        return transmitions.stream().anyMatch(new Predicate<Transmition>() {
            @Override
            public boolean test(Transmition transmition) {
                return transmition.isParticipating(httpSession);
            }
        });
    }

    public Transmition fetch(String transmitionCode) {
        return transmitions.stream().filter(transmition -> transmition.getTransmitionCode().equals(transmitionCode)).findFirst().get();
    }

    public Transmition fetch(Session socketSession) {
        // TODO Buscar pelo SOCKET DO USUARIO
    }

    private void updateTransmition(HttpSession httpSession, Session socketSession){
        transmitions.forEach(new Consumer<Transmition>() {
            @Override
            public void accept(Transmition transmition) {
                if (transmition.isParticipating(httpSession)) {
                    transmition.fetchConnection(httpSession).addSocket(socketSession);
                }
            }
        });
    }

    private void addTransmition(Connection connection){
        Transmition transmition = new Transmition();
        transmition.getIn(connection);
        transmitions.add(transmition);
    }
}
