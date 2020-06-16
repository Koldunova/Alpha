package com.example.alpha.domain;


public class Note {
    private String id;
    private String title;
    private String text_note;
    private String date;
    
    public Note() {
    }
    
    public Note(String id, String title,String text_note,String date) {
        setDate(date);
        setId(id);
        setText_note(text_note);
        setTitle(title);
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

    public String getText_note() {
        return text_note;
    }

    public void setText_note(String text_note) {
        this.text_note = text_note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Note [id=" + id + ", title=" + title + ", text_note=" + text_note + ", date=" + date + "]";
    }
}
