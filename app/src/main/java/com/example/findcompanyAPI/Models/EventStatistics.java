package com.example.findcompanyAPI.Models;

import java.math.BigInteger;

public class EventStatistics {
    public EventStatistics(String id_event, String name_event, Integer amount) {
        this.id_event = id_event;
        this.name_event = name_event;
        this.amount = amount;
    }

    private String id_event;
    public String getId_event(){ return id_event;}

    private String name_event;
    public String getName_event(){ return name_event;}

    private Integer amount;
    public Integer getAmount(){ return amount;}

}
