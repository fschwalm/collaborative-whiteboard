package transmition;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Created by drferreira on 18/12/14.
 */
public class Connection {
    private Session socketSession;
    private String httpSessionId;

    public Connection(Session socketSession, HttpSession httpSession) {
        this.httpSessionId = httpSession.getId();

        this.socketSession = socketSession;
    }

    public Session getSocketSession() {
        return socketSession;
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }
}
