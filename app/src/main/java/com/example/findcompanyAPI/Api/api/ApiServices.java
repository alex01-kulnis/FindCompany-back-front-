package com.example.findcompanyAPI.Api.api;

import com.example.findcompanyAPI.Models.ConfirmVisit;
import com.example.findcompanyAPI.Models.Event;
import com.example.findcompanyAPI.Models.EventHistory;
import com.example.findcompanyAPI.Models.EventStatistics;
import com.example.findcompanyAPI.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiServices {
    @GET("api/events")
    Call<List<Event>> getEvents();

    @GET("api/history-visiting-events")
    Call<List<EventHistory>> getHistoryEvents();

    @GET("api/history-visiting-events/statistics")
    Call<List<EventStatistics>> getStatistics();

    @GET("api/confirm-visiting-events")
    Call<List<ConfirmVisit>> getConfirmEvents();

    @POST("api/login")
    Call<User> auth(@Body User user);
}
