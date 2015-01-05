package br.org.tutty.collaborative_whiteboard.cw.context;

import br.org.tutty.collaborative_whiteboard.cw.model.Project;

import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by drferreira on 03/01/15.
 */
@SessionScoped
public class SelectedDataContext implements Serializable{
    private Project selectedProject;

    public Project getSelectedProject() throws UnselectedException {
        // TODO gambiarra
        selectedProject = new Project("TESTE");
        return (Project) isSelected(selectedProject);
    }

    public void selectProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    private Object isSelected(Object value) throws UnselectedException {
        if (value != null){
            return value;
        }else {
            throw new UnselectedException();
        }
    }

    public class UnselectedException extends Exception {

    }
}
