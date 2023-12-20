package com.example.isable_capstone.api

import com.example.isable_capstone.api.response.MapsResponseItem
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("/map")
    fun getMapsData(): Call<List<MapsResponseItem>>
}