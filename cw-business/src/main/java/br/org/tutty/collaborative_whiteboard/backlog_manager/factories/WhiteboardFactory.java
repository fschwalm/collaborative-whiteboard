package br.org.tutty.collaborative_whiteboard.backlog_manager.factories;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import br.org.tutty.Equalizer;
import br.org.tutty.backlog_manager.TaskDao;
import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;
import dtos.*;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by drferreira on 13/07/15.
 */
public class WhiteboardFactory {

    private WhiteboardDao whiteboardDao;
    private TaskDao taskDao;

    public WhiteboardFactory(WhiteboardDao whiteboardDao, TaskDao taskDao) {

        this.whiteboardDao = whiteboardDao;
        this.taskDao = taskDao;
    }

    public WhiteboardMailable builderMailableWhiteboard() {
        WhiteboardMailable whiteboardMailable = new WhiteboardMailable();
        builderAllStages(whiteboardMailable, whiteboardDao.fetchAllStages());

        for (Task task : taskDao.fetchForWhiteboard()) {
            Story story = task.getStory();
            Stage stage = task.getStage();

            try {
                StagesMailable stagesMailable = whiteboardMailable.getStageByName(stage.getName());
                StoryMailable storyMailable = builderStory(stagesMailable, story);
                builderTask(storyMailable, task);

                whiteboardMailable.addStage(stagesMailable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return whiteboardMailable;
    }

    private void builderAllStages(WhiteboardMailable whiteboardMailable, Set<Stage> stages) {
        stages.forEach(new Consumer<Stage>() {
            @Override
            public void accept(Stage stage) {
                try {
                    builderStage(whiteboardMailable, stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private StagesMailable builderStage(WhiteboardMailable whiteboardMailable, Stage stage) throws NoSuchFieldException, IllegalAccessException {
        StagesMailable stagesMailable;

        stagesMailable = new StagesMailable();
        Equalizer.equalize(stage, stagesMailable);
        whiteboardMailable.addStage(stagesMailable);

        return stagesMailable;
    }

    private StoryMailable builderStory(StagesMailable stagesMailable, Story story) throws NoSuchFieldException, IllegalAccessException {
        StoryMailable storyMailable;
        if (!stagesMailable.existStory(story.getCode())) {
            storyMailable = new StoryMailable();
            Equalizer.equalize(story, storyMailable);
            stagesMailable.addStory(storyMailable);
        } else {
            storyMailable = stagesMailable.getStoryByCode(story.getCode());
        }

        return storyMailable;
    }

    private void builderTask(StoryMailable storyMailable, Task task) throws NoSuchFieldException, IllegalAccessException {
        TaskMailable taskMailable;

        if (!storyMailable.existTask(task.getCode())) {
            taskMailable = new TaskMailable();
            TaskStatusMailable taskStatusMailable = builderTaskStatus(task);

            taskMailable.setTaskStatus(taskStatusMailable);

            Equalizer.equalize(task, taskMailable);
            storyMailable.addTask(taskMailable);
        }
    }

    private TaskStatusMailable builderTaskStatus(Task task) throws NoSuchFieldException, IllegalAccessException {
        try {
            TaskStatusMailable taskStatusMailable = new TaskStatusMailable();

            TaskStatusLog taskStatusLog = taskDao.fetchTaskStatusLog(task);
            Equalizer.equalize(taskStatusLog, taskStatusMailable);

            taskStatusMailable.setValue(taskStatusLog.getTaskStatus().name());
            taskStatusMailable.setUsername(taskStatusLog.getUser().getFullName());

            return taskStatusMailable;
        } catch (DataNotFoundException e) {return null;}
    }

}
