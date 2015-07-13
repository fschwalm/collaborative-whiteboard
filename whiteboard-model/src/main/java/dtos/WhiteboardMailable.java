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

    public StagesMailable getStageByName(String name){
        return stages.stream().filter(stage -> stage.getName().equals(name)).findAny().get();
    }

    public Date getUpdateDate() {
        return updateDate;
    }
}
