package com.example.isable_capstone.api

import com.example.isable_capstone.api.response.LearningResponseItem
import com.example.isable_capstone.api.response.MapsResponseItem
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface ApiService {
    @GET("/map")
    fun getMapsData(): Call<List<MapsResponseItem>>

    @GET("/{path}")
    fun getAll(@Path("path") path: String): Call<ArrayList<LearningResponseItem>>
}