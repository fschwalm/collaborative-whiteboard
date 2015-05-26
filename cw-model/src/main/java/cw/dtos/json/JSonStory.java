package cw.dtos.json;

import java.io.Serializable;

/**
 * Created by drferreira on 19/05/15.
 */
public class JSonStory implements Serializable {
    private String code;
    private JSonStage stage;
    private String subject;
    private String branch;
    private JSonUser author;

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

    public JSonStage getStage() {
        return stage;
    }

    public void setStage(JSonStage stage) {
        this.stage = stage;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranch() {
        return branch;
    }


    public void setAuthor(JSonUser author) {
        this.author = author;
    }

    public JSonUser getAuthor() {
        return author;
    }
}
