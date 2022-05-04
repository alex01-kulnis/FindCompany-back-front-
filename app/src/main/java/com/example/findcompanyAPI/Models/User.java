package com.example.findcompanyAPI.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("id_user")
    private Integer id_user;

    @SerializedName("firstname")
    private String firstname;

    public User(String firstname, String secondname, String login, String password) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.login = login;
        this.password = password;
    }

    @SerializedName("secondname")
    private String secondname;

    @SerializedName("login")
    private String login;

    @SerializedName("token")
    private String token;

    @SerializedName("password")
    private String password;

    public User(String loginn, String passwordd) {
        this.login = loginn;
        this.password = passwordd;
    }

    public int getId_user() {
        return id_user;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getToken() {
        return token;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
