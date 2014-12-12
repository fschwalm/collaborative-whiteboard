package br.org.tutty.collaborative_whiteboard.transmition;

import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;
import org.json.JSONObject;

/**
 * Created by drferreira on 12/12/14.
 */
public interface MessageBuilder {

    public JSONObject getMessage() throws MessageMountException;

}
