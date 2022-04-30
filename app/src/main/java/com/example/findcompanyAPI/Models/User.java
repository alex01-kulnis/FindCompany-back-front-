package com.example.findcompanyAPI.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {


    @SerializedName("id_user")
    private int id_user;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("secondname")
    private String secondname;

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    public int getId_user() {
        return id_user;
    }

    public String getFirstname() {
        return firstname;
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
