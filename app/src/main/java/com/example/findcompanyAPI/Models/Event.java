package com.example.findcompanyAPI.Models;

import java.io.Serializable;

public class Event implements Serializable {

//    public Event(String id_event, String id_user,String id_creator, String name_event, String place_event, String data_and_time_event, String max_participants_event) {
//        this.id_event = id_event;
//        this.id_creator = id_creator;
//        this.id_user = id_user;
//        this.name_event = name_event;
//        this.place_event = place_event;
//        this.data_and_time_event = data_and_time_event;
//        this.max_participants_event = max_participants_event;
//    }

    public Event(String name_event) {
        this.name_event = name_event;
    }

    public Event(Integer id_event, Integer id_creator, String name_event, String place_event, String data_and_time_event, Integer maxParticipacion) {
        this.id_event = id_event;
        this.id_creator = id_creator;
        this.name_event = name_event;
        this.place_event = place_event;
        this.data_and_time_event = data_and_time_event;
        this.max_participants_event = maxParticipacion;
    }

    private Integer id_event;

    public Event(String name_event, String place_event, String data_and_time_event, Integer maxParticipacion) {
        this.name_event = name_event;
        this.place_event = place_event;
        this.data_and_time_event = data_and_time_event;
        this.max_participants_event = maxParticipacion;
    }

    public Integer getId_event(){ return id_event;}

    private String id_user;
    public String getId_user(){ return id_user;}

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

}
