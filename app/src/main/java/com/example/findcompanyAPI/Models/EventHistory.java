package com.example.findcompanyAPI.Models;

import java.io.Serializable;

public class EventHistory implements Serializable {

    public EventHistory(Integer id,Integer id_event, Integer id_user,Integer id_creator,
                        String name_event, String place_event,
                        String data_and_time_event, Integer max_participants_event) {
        this.id = id;
        this.id_event = id_event;
        this.id_creator = id_creator;
        this.id_user = id_user;
        this.place_event = place_event;
        this.name_event = name_event;
        this.data_and_time_event = data_and_time_event;
        this.max_participants_event = max_participants_event;
    }

    private Integer  id;
    public Integer getId(){ return id;}

    private Integer id_event;
    public Integer getId_event(){ return id_event;}

    private Integer id_user;
    public Integer getId_user(){ return id_user;}

    private Integer id_creator;
    public Integer getId_creator(){ return id_creator;}
    //
    private String name_event;
    public String getName_event(){
        return this.name_event;
    }
    public void setName_event(String info){
        this.name_event = info;
    }
    //
    private String place_event;
    public String getPlace_event(){
        return place_event;
    }
    public void setPlace_event(String info){
        this.place_event = info;
    }
    //
    private String data_and_time_event;
    public String getDataAndtime_event(){
        return data_and_time_event;
    }
    public void setDataAndtime_event(String info){
        this.data_and_time_event = info;
    }
    //
    private Integer max_participants_event;
    public Integer getMaxParticipants_event(){ return max_participants_event;}





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

