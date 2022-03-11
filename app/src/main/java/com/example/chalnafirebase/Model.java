package com.example.chalnafirebase;
public class Model {
    String id, title, story,date;

    public Model() {
    }

    public Model(String id, String title, String story, String date) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
