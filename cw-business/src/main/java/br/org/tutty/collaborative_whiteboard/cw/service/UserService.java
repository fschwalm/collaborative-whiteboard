package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.model.User;

/**
 * Created by drferreira on 16/12/14.
 */
public interface UserService {

    public User fetch(String email) throws DataNotFoundException;

}
