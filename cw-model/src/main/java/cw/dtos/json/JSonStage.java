package cw.dtos.json;


import java.io.Serializable;
import java.util.Set;

/**
 * Created by drferreira on 19/05/15.
 */
public class JSonStage implements Serializable {
    public String name;

    private Set<JSonStory> stories;

    public JSonStage() {
    }

    public Set<JSonStory> getStories() {
        return stories;
    }

    public String getName() {
        return name;
    }

    public void setName(String getName) {
        this.name = getName;
    }

    public void addStory(JSonStory jSonStory) {
        stories.add(jSonStory);
    }

    public void setStories(Set<JSonStory> stories) {
        this.stories = stories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSonStage jSonStage = (JSonStage) o;

        if (name != null ? !name.equals(jSonStage.name) : jSonStage.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
