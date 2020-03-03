package domain;

public class Report {
    private String personName;
    private Object document;
    
    public Report() {}

    public Object getDocument() {
        return document;
    }

    public void setDocument(Object document) {
        this.document = document;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
