package com.example.findcompanyAPI.Models;

import java.io.Serializable;

public class EventHistory implements Serializable {

    public EventHistory(int id,int id_event, int id_user,int id_creator, String name_event, String place_event, String dataAndtime_event, int maxParticipants_event) {
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





//    private Date DateEx;
//    public String getDateEx(){ return this.DateEx.toString();}
//
//    private String Amount;
//    public String getAmount() {
//        return this.Amount;
//    }
//
//    public void setAmount(String amount) {
//        Amount = amount;
//    }
}

// db.execSQL("create table Events ("
//         + "id_event integer primary key autoincrement not null,"
//         + "id_user integer not null,                           "
//         + "name_event text not null,                           "
//         + "place_event text not null,                          "
//         + "dataAndtime_event Date not null,                    "
//         + "maxParticipants_event integer not null,             "
//         + " foreign key(id_user) references Users(id_user)     "
//         + " on delete cascade on update cascade              );"

