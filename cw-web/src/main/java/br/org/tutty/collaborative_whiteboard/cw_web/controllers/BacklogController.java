package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryCreation;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryEdition;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ReorderEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
@Named
@ViewScoped
public class BacklogController extends GenericController implements Serializable {
    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private SessionContext sessionContext;

    @Inject
    private UserService userService;

    @Inject
    private StoryCreation storyCreation;

    @Inject
    private StoryEdition storyEdition;

    @Inject
    private ProjectService projectService;

    private List<Story> stories;

    @PostConstruct
    public void setUp() throws EncryptedException, IOException {
        stories = fetchStories();
    }

    public List<Story> fetchStories() throws IOException {
        try {
            return backlogManagerService.fetchAllStories();

        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    public void onRowReorder(ReorderEvent event) throws IOException {
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "backlog.change_priority", "save.pending");
    }

    public void removeStory() throws IOException {
        User user = sessionContext.getLoggedUser().getUser();
//        selectedStory.remove(user);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "backlog.removed_story", "save.pending");
    }

    public void updateBacklog() throws IOException, EncryptedException {
//        backlogManagerService.updateBacklog(stories);
//        setUp();
//        facesMessageUtil.showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.update");
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
