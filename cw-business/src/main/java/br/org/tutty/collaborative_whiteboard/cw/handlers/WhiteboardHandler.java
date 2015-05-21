package br.org.tutty.collaborative_whiteboard.cw.handlers;

import backlog_manager.entities.Story;
import cw.dtos.json.JSonStage;
import cw.dtos.json.Whiteboard;
import cw.entities.Stage;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by drferreira on 20/05/15.
 */
@ApplicationScoped
public class WhiteboardHandler implements Serializable {
    private final Set<Session> sessions = new HashSet<>();

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void broadcast(String whiteboard) {
        getSessions().stream().forEach(session -> send(whiteboard, session));
    }

    public void send(String whiteboard, Session target) {
        try {
            target.getBasicRemote().sendObject(whiteboard);
        } catch (IOException | EncodeException e) {
        }
    }

    public Whiteboard builderWhiteboard(List<Stage> stages, Set<Story> stories) {
        Whiteboard whiteboard = new Whiteboard();

        stages.stream().forEach(new Consumer<Stage>() {
            @Override
            public void accept(Stage stage) {
                JSonStage jSonStage = stage.toJSON();
                whiteboard.addStage(jSonStage);

                stories.stream().forEach(new Consumer<Story>() {
                    @Override
                    public void accept(Story story) {
                        if (story.getStage().toJSON().equals(jSonStage)) {
                            jSonStage.addStory(story.toJson());
                        }
                    }
                });
            }
        });

        return whiteboard;
    }
}
