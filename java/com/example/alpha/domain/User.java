package com.example.alpha.domain;

public class User {
    private int idUser;
    private int money;

    public User() {
    }

    public User(int id, int money) {
        this.idUser = id;
        this.money = money;
    }

    public void setIdUser(int id) {
        this.idUser = id;
    }

    public int getIdUser() {
        return this.idUser;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", money=" + money +
                '}';
    }
}
