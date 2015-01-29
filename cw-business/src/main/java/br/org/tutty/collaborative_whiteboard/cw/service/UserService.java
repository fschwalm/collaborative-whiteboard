package br.org.tutty.collaborative_whiteboard.cw.service;

import cw.entities.User;
import cw.exceptions.DataNotFoundException;

/**
 * Created by drferreira on 16/12/14.
 */
public interface UserService {

    public User fetch(String email) throws DataNotFoundException;

}
