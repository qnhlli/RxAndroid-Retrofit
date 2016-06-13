package com.example.qnhlli.myrxandroid2;

/**
 * Created by qnhlli on 2016/6/12.
 */
public class Repo {
    public String name;
    public String description;
    public String url;

    public Repo(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public String toString() {
        return name + ": " + url;
    }
}
