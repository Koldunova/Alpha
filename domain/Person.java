package domain;

public class Person {
    private int idPerson;
    private String personName;
    private String position;
    private String description;
    private Object pic;
    private Dna dna;
    
    public int getIdPerson() {
        return idPerson;
    }
    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }
    
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Object getPic() {
        return pic;
    }
    public void setPic(Object pic) {
        this.pic = pic;
    }
    
    public Dna getDna() {
        return dna;
    }
    public void setDna(Dna dna) {
        this.dna = dna;
    }
    
}
