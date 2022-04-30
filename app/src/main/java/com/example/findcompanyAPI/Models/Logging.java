package com.example.findcompanyAPI.Models;

public class Logging {
    public Logging(int id, String reason , String date_and_time ) {
        this.id = id;
        this.reason  = reason ;
        this.date_and_time  = date_and_time ;

    }

    private int id;
    public String getId(){ return Integer.toString(id);}

    private String reason ;
    public String getReason(){
        return this.reason  ;
    }
    public void setFirstname (String info){
        this.reason   = info;
    }

    private String date_and_time ;
    public String getDate_and_time(){
        return this.date_and_time;
    }
    public void setDate_and_time(String info){
        this.date_and_time = info;
    }

    private String login;
    public String getLogin (){
        return this.login ;
    }
    public void setLogin (String info){
        this.login  = info;
    }

    private String password;
    public String getPassword  (){
        return this.password  ;
    }
    public void setPassword  (String info){
        this.password   = info;
    }
}
