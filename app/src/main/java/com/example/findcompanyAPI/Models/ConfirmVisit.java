package com.example.findcompanyAPI.Models;

import java.io.Serializable;

public class ConfirmVisit implements Serializable {

    public ConfirmVisit(int id,int id_event, int id_user,int id_creator, String name_event, String place_event, String dataAndtime_event, int maxParticipants_event,
                        String surname) {
        this.Surname_user = surname;
        this.Id = id;
        this.Id_event = id_event;
        this.Id_creator = id_creator;
        this.Id_user = id_user;
        this.Place_event = place_event;
        this.Name_event = name_event;
        this.DataAndtime_event = dataAndtime_event;
        this.MaxParticipants_event = maxParticipants_event;
    }

    private int Id;
    public String getId(){ return Integer.toString(Id);}

    private int Id_event;
    public String getId_event(){ return Integer.toString(Id_event);}

    private int Id_user;
    public String getId_user(){ return Integer.toString(Id_user);}

    private int Id_creator;
    public String getId_creator(){ return Integer.toString(Id_creator);}
    //
    private String Name_event;
    public String getName_event(){
        return this.Name_event;
    }
    public void setName_event(String info){
        this.Name_event = info;
    }
    //
    private String Surname_user;
    public String getSurname_user(){
        return this.Surname_user;
    }
    public void setSurname_user(String info){
        this.Surname_user = info;
    }
    //
    private String Place_event;
    public String getPlace_event(){
        return Place_event;
    }
    public void setPlace_event(String info){
        this.Place_event = info;
    }
    //
    private String DataAndtime_event;
    public String getDataAndtime_event(){
        return DataAndtime_event;
    }
    public void setDataAndtime_event(String info){
        this.Place_event = info;
    }
    //
    private int MaxParticipants_event;
    public String getMaxParticipants_event(){ return Integer.toString(MaxParticipants_event);}
}
