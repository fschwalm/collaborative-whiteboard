package br.org.tutty.collaborative_whiteboard.cw.service;

import cw.entities.Project;
import cw.exceptions.NameInUseException;

/**
 * Created by root on 16/03/15.
 */
public interface ProjectService {
    public Project createProject(String projectName) throws NameInUseException;
}
