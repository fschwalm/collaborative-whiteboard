package br.org.tutty.transmition;

import br.org.tutty.collaborative_whiteboard.Dao;
import transmition.entities.SentMessage;

import java.util.List;

/**
 * Created by drferreira on 29/04/15.
 */
public interface TransmitionDao extends Dao {
    List<SentMessage> fetchLastMessages(Integer defaultSizeLastMessages);
}
