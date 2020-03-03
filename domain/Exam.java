package domain;

public class Exam {
    private String personName;
    private Object audio;
    private String title;
    private String description;
    
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    public Object getAudio() {
        return audio;
    }
    public void setAudio(Object audio) {
        this.audio = audio;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
