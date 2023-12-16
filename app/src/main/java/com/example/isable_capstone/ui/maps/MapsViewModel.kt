package com.example.isable_capstone.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.isable_capstone.api.ApiInterface
import com.example.isable_capstone.api.ApiService
import com.example.isable_capstone.ui.maps.model.dataMap.ModelResults
import com.example.isable_capstone.ui.maps.model.response.ModelResultNearby
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.util.*

class MapsViewModel: ViewModel() {

    private val modelResultsMutableLiveData = MutableLiveData<ArrayList<ModelResults>>()
    private var strApiKey = "AIzaSyDAVtpMtQAx-EUkBr92Uy4nC8feEQOB8cU"

    fun setMarkerLocation(strLocation: String) {
        val apiService: ApiInterface = ApiService.getMaps()
        val call = apiService.getDataResult(strApiKey, "Tambal Ban", strLocation, "distance")

        call.enqueue(object : Callback<ModelResultNearby> {
            override fun onResponse(
                call: Call<ModelResultNearby>,
                response: Response<ModelResultNearby>
            ) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    val items = ArrayList(
                        response.body()!!.modelResults
                    )
                    modelResultsMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ModelResultNearby>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }

    fun getMarkerLocation(): LiveData<ArrayList<ModelResults>> = modelResultsMutableLiveData
}