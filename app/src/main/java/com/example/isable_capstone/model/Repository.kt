package com.example.isable_capstone.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.DiffUtil
import com.example.isable_capstone.api.ApiConfig

import com.example.isable_capstone.api.ApiService
import com.example.isable_capstone.response.AllAngkaResponse
import com.example.isable_capstone.response.AllAngkaResponseItem
import com.example.isable_capstone.ui.translate.CameraActivity
import com.google.gson.Gson
import retrofit2.HttpException

class Repository private constructor(private val apiService: ApiService){

    fun getAll(modul: String)=liveData{
        Log.d("TEST MODEL","APAAA :${modul}")
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getAll(modul)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AllAngkaResponse::class.java)
            emit(errorResponse.let { ResultState.Error("Error pada api") })
        }
//    : List<AllAngkaResponseItem> {
//        Log.d(TAG, "MODUL YG TERPILIH: ${modul}")
//        return apiService.getAll(modul) // Sesuaikan dengan struktur response API Anda


    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(apiService: ApiService): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}