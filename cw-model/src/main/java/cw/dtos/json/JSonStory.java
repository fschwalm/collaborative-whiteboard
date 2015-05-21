package cw.dtos.json;

import java.io.Serializable;

/**
 * Created by drferreira on 19/05/15.
 */
public class JSonStory implements Serializable {
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSonStory jSonStory = (JSonStory) o;

        if (code != null ? !code.equals(jSonStory.code) : jSonStory.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
