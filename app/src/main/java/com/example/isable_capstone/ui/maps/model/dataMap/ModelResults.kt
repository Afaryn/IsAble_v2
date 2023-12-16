package com.example.isable_capstone.ui.maps.model.dataMap

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ModelGeometry {
    @SerializedName("location")
    lateinit var modelLocation: ModelLocation
}
class ModelLocation {
    @SerializedName("lat")
    var lat: Double = 0.0

    @SerializedName("lng")
    var lng: Double = 0.0
}
class ModelResults : Serializable {
    @SerializedName("geometry")
    lateinit var modelGeometry: ModelGeometry

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("vicinity")
    lateinit var vicinity: String

    @SerializedName("place_id")
    lateinit var placeId: String

    @SerializedName("rating")
    var rating = 0.0

}
