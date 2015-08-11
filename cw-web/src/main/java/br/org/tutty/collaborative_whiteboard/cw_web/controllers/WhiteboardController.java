package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskActionService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskManagerService;
import br.org.tutty.collaborative_whiteboard.cw.handlers.WhiteboardHandler;
import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;
import cw.entities.Stage;
import cw.exceptions.*;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by drferreira on 20/05/15.
 */
@ViewScoped
@Named
public class WhiteboardController extends GenericController implements Serializable {
    @Inject
    private WhiteboardService whiteboardService;

    @Inject
    private WhiteboardHandler whiteboardHandler;

    @Inject
    private TaskActionService taskActionService;

    private String stageNameForCreation;

    public void createStage() throws DataNotFoundException {
        Stage stage = new Stage(stageNameForCreation);
        whiteboardService.createStage(stage);
        stageNameForCreation = null;
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "whiteboard.stage.created.sucess", "whiteboard.stage.created.detail");
    }

    public void taskNext() {
        String task_code = getTaskCode();
        try {
            taskActionService.forward(task_code);
        } catch (DataNotFoundException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.stop.error", "task.edition.code_not_found");
        } catch (LastStageException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.next.error", "task.edition.next.error.last_stage");
        }
    }

    public void taskPlay() {
        String task_code = getTaskCode();

        try {
            taskActionService.init(task_code);
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.init.success", "task.edition.init");

        } catch (DataNotFoundException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.init.error", "task.edition.code_not_found");
        } catch (InitTaskException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.init.error", "task.edition.in_use.detail");
        }
    }

    public void taskStop() {
        String task_code = getTaskCode();

        try {
            taskActionService.stop(task_code);
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.stop.success", "task.edition.stop");

        } catch (DataNotFoundException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.stop.error", "task.edition.code_not_found");
        } catch (StopTaskException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.stop.error", "task.edition.not_in_use");
        }
    }

    public void taskPrev() {
        String task_code = getTaskCode();

        try {
            taskActionService.backward(task_code);
        } catch (DataNotFoundException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.stop.error", "task.edition.code_not_found");
        } catch (FirstStageException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.last.error", "task.edition.last.error.first_stage");
        }
    }

    public void taskFinalize() {
        String task_code = getTaskCode();

        try {
            taskActionService.end(task_code);
        } catch (DataNotFoundException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.end.error", "task.edition.code_not_found");
        } catch (EndTaskException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.end.error", "task.edition.end.error.detail");
        }
    }

    private String getTaskCode() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("task_code");
    }

    public void cancel() {
        stageNameForCreation = null;
    }

    public String getStageNameForCreation() {
        return stageNameForCreation;
    }

    public void setStageNameForCreation(String name) {
        this.stageNameForCreation = name;
    }
}
