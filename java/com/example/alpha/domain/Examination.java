package com.example.alpha.domain;

public class Examination {
    private String title;
    private String audio;
    private String description;
    private String person_name;

    public Examination(String title, String audio, String description, String person_name) {
        this.title = title;
        this.audio = audio;
        this.description = description;
        this.person_name = person_name;
    }

    public Examination() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }
}
