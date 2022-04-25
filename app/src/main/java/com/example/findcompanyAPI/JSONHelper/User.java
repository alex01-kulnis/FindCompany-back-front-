package com.example.findcompanyAPI.JSONHelper;

public class User {

    private String name;
    private String mark;

    public User(String name, String mark){
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public  String toString(){
        return "Название:" + name + " Пометка: " + mark;
    }
}
