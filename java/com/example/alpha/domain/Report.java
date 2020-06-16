package com.example.alpha.domain;

public class Report {
    private String person_name;
    private String document;
    
    public Report() {
    }
    
    public Report(String person_name,String document) {
        setDocument(document);
        setPerson_name(person_name);
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
