package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerServiceBean;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import cw.exceptions.DataNotFoundException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 22/06/15.
 */
@Named
@ViewScoped
public class IterationController extends GenericController implements Serializable {

    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private IterationService iterationService;

    private String name;
    private Date init;
    private Date end;

    private List<Story> selected;

    @PostConstruct
    public void setUp() throws IOException {
        name = null;
        selected = new ArrayList<>();
    }

    public void create() throws IOException {
        iterationService.create(selected, name, init, end);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.iteration.success");
        setUp();
    }

    public List<Story> fetchAvailables() throws IOException {
        try{
            return backlogManagerService.fetchAnalyzedStories();
        }catch (DataNotFoundException e){
            return new ArrayList<>();
        }
    }

    public List<Story> getSelected() {
        return selected;
    }

    public void setSelected(List<Story> selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInit() {
        return init;
    }

    public void setInit(Date init) {
        this.init = init;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
