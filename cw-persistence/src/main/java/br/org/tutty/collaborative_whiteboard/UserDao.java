package br.org.tutty.collaborative_whiteboard;

import cw.entities.User;
import cw.exceptions.DataNotFoundException;

/**
 * Created by drferreira on 29/01/15.
 */
public interface UserDao {
    User fetch(String email) throws DataNotFoundException;
}
