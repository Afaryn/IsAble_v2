package com.example.isable_capstone.api

import retrofit2.http.GET
import com.example.isable_capstone.ui.maps.model.response.ModelResultNearby
import retrofit2.Call
import retrofit2.http.Query

interface ApiInterface {
    @GET("place/nearbysearch/json")
    fun getDataResult(
        @Query("key") key: String,
        @Query("keyword") keyword: String,
        @Query("location") location: String,
        @Query("rankby") rankby: String
    ): Call<ModelResultNearby>


}