package domain;

public class User {
    private int idUser;
    private String emailUser;
    private String password;

    public User(){
    }

    public User(int id, String email, String password){
        this.idUser=id;
        this.emailUser=email;
        this.password=password;
    }

    public void setIdUser(int id){
        this.idUser=id;
    }

    public void setEmailUser(String email){
        this.emailUser=email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public int getIdUser(){
        return this.idUser;
    }

    public String getEmailUser(){
        return this.emailUser;
    }

    public String getPassword(){
        return this.password;
    }
}
