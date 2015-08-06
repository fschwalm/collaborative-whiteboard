package br.org.tutty.collaborative_whiteboard.backlog_manager.factories;

import backlog_manager.entities.Story;
import br.org.tutty.backlog_manager.StoryDao;
import br.org.tutty.backlog_manager.TaskDao;
import cw.entities.Project;
import cw.entities.ProjectArea;

/**
 * Created by drferreira on 26/05/15.
 */
public class CodeFactory {
    private static String SEPARATOR_ID = "-";

    public static String story(StoryDao storyDao, Project project, ProjectArea projectArea){
        Long sequence = (storyDao.getNextSequenceStory(project) + 1);

        String projectName = projectArea.getProject().getPrefix();
        String projectAreaAbbreviation = projectArea.getPrefix();

        StringBuffer code = new StringBuffer(projectName);
        code.append(SEPARATOR_ID);
        code.append(projectAreaAbbreviation);
        code.append(SEPARATOR_ID);
        code.append(sequence.toString());

        return code.toString();
    }

    public static String branch(Project project, String storyCode){
        String projectName = project.getPrefix();

        String branch = storyCode.replace(projectName+ SEPARATOR_ID, "");
        return branch;
    }

    public static String task(TaskDao taskDao, Story story){
        Long sequence = (taskDao.getNextSequenceTask(story) + 1);

        StringBuffer code = new StringBuffer(story.getCode());
        code.append(SEPARATOR_ID);
        code.append(sequence.toString());

        return code.toString();
    }
}
