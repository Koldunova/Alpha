package domain;

public class Note {
    private String title;
    private String textNote;
    private String date;
    private String idUser;
    private int idNote;
    
    public Note() {}
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTextNote() {
        return textNote;
    }
    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }
    
    
    
}
