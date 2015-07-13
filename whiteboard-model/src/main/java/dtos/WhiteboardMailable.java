package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by drferreira on 10/07/15.
 */
public class WhiteboardMailable implements Serializable {
    private Set<StagesMailable> stages;
    private Date updateDate;

    public WhiteboardMailable() {
        updateDate = new Date();
        stages = new HashSet<>();
    }

    public void addStage(StagesMailable stagesMailable){
        stages.add(stagesMailable);
    }

    public Boolean existStage(String name){
        return stages.stream().anyMatch(stage -> stage.getName().equals(name));
    }

    public StagesMailable getStageByName(String name){
        return stages.stream().filter(stage -> stage.getName().equals(name)).findAny().get();
    }

    public Set<StagesMailable> getStages() {
        return stages;
    }

    public Date getUpdateDate() {
        return updateDate;
    }
}
