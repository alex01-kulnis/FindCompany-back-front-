package com.example.findcompanyAPI.Api.Repository

import com.example.findcompanyAPI.Models.Event
import retrofit2.Response

public class Repository {
    suspend fun getEvents(): Response<Event> {
        return RetrofitInstance.api.getEvents();
    }
}