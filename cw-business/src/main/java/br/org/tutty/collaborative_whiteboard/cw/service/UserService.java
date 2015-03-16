package br.org.tutty.collaborative_whiteboard.cw.service;

import cw.entities.Project;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 16/12/14.
 */
public interface UserService {

    public User fetch(String email) throws DataNotFoundException;

    public List<Project> fetchProjects() throws DataNotFoundException;

    Boolean hasSomeProject();
}
