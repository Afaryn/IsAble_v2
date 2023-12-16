package com.example.isable_capstone.ui.maps.model.response

import com.google.gson.annotations.SerializedName
import com.example.isable_capstone.ui.maps.model.dataMap.ModelResults

class ModelResultNearby {
    @SerializedName("results")
    lateinit var modelResults: List<ModelResults>
}