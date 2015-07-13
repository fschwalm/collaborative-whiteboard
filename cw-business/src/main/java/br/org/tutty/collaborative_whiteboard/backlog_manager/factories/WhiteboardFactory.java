package br.org.tutty.collaborative_whiteboard.backlog_manager.factories;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;
import br.org.tutty.Equalizer;
import cw.entities.Stage;
import dtos.StagesMailable;
import dtos.StoryMailable;
import dtos.TaskMailable;
import dtos.WhiteboardMailable;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by drferreira on 13/07/15.
 */
public class WhiteboardFactory {

    public static WhiteboardMailable builderMailableWhiteboard(List<Task> tasks, Set<Stage> stages) {
        WhiteboardMailable whiteboardMailable = new WhiteboardMailable();
        builderAllStages(whiteboardMailable, stages);

        for (Task task : tasks) {
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

    private static void builderAllStages(WhiteboardMailable whiteboardMailable, Set<Stage> stages) {
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

    private static StagesMailable builderStage(WhiteboardMailable whiteboardMailable, Stage stage) throws NoSuchFieldException, IllegalAccessException {
        StagesMailable stagesMailable;

        stagesMailable = new StagesMailable();
        Equalizer.equalize(stage, stagesMailable);
        whiteboardMailable.addStage(stagesMailable);

        return stagesMailable;
    }

    private static StoryMailable builderStory(StagesMailable stagesMailable, Story story) throws NoSuchFieldException, IllegalAccessException {
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

    private static void builderTask(StoryMailable storyMailable, Task task) throws NoSuchFieldException, IllegalAccessException {
        TaskMailable taskMailable;

        if (!storyMailable.existTask(task.getCode())) {
            taskMailable = new TaskMailable();
            Equalizer.equalize(task, taskMailable);
            storyMailable.addTask(taskMailable);
        }
    }
}
