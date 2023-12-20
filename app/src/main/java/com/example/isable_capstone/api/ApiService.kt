package com.example.isable_capstone.api

import com.example.isable_capstone.api.response.MapsResponseItem
import com.example.isable_capstone.response.AllAngkaResponseItem
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface ApiService {
    @GET("/map")
    fun getMapsData(): Call<List<MapsResponseItem>>

    @GET("/{modul}")
    fun getAll(@Path("modul") modul:String):List<AllAngkaResponseItem>


}