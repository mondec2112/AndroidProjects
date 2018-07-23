package com.example.monsanity.edusoft.Container;

/**
 * Created by monsanity on 3/12/18.
 */

public class Subjects {

    String id;
    String name;
    String credit;
    String pre;
    String level;

    public Subjects() {
    }

    public Subjects(String id, String name, String credit, String pre, String level) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.pre = pre;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
