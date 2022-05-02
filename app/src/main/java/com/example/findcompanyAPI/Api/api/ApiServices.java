package com.example.findcompanyAPI.Api.api;

import com.example.findcompanyAPI.Models.ConfirmVisit;
import com.example.findcompanyAPI.Models.Event;
import com.example.findcompanyAPI.Models.EventHistory;
import com.example.findcompanyAPI.Models.EventStatistics;
import com.example.findcompanyAPI.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiServices {
    @GET("api/events")
    Call<List<Event>> getEvents();

    @PUT("api/search")
    Call<List<Event>> getSearchEvents(@Body Event Event);

    @GET("api/history-visiting-events")
    Call<List<EventHistory>> getHistoryEvents();

    @GET("api/history-visiting-events/statistics")
    Call<List<EventStatistics>> getStatistics();

    @GET("api/confirm-visiting-events")
    Call<List<ConfirmVisit>> getConfirmEvents();

    @POST("api/login")
    Call<User> auth(@Body User user);

    @POST("api/registration")
    Call<User> registration(@Body User user);

    @POST("api/apply-event")
    Call<Event> apply(@Body Event Event);

    @POST("api/create-event")
    Call<Event> createEvent(@Body Event Event);

    @POST("api/confirm-visiting-events")
    Call<ConfirmVisit> сonfirmVisit(@Body ConfirmVisit сonfirmVisit);

    @PATCH("api/confirm-visiting-events")
    Call<ConfirmVisit> deleteConfirmVisit(@Body ConfirmVisit сonfirmVisit);
}
