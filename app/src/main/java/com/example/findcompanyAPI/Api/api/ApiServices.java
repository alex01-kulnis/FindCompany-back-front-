package com.example.findcompanyAPI.Api.api;

import com.example.findcompanyAPI.Models.Event;
import com.example.findcompanyAPI.Models.EventHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {
    @GET("api/events")
    Call<List<Event>> getEvents();

    @GET("api/confirm-visiting-events")
    Call<List<EventHistory>> getHistoryEvents();

    @POST("api/confirm-visiting-events")
    Call<List<EventHistory>> getHistoryEvents();
}
