package com.example.isable_capstone.ui.maps

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.isable_capstone.R
import com.example.isable_capstone.ui.maps.model.dataMap.DummyData
import com.example.isable_capstone.ui.maps.model.dataMap.ModelResults
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker

class CustomMarker(private val context: Context) : InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker): View? {
        val view = (context as AppCompatActivity)
            .layoutInflater
            .inflate(R.layout.custom_marker, null)

        val tvNamaLokasi = view.findViewById<TextView>(R.id.tvNamaLokasi)
        val tvAlamat = view.findViewById<TextView>(R.id.tvAlamat)
        val infoWindowData = marker.tag as DummyData

        tvNamaLokasi.text = infoWindowData.name
        tvAlamat.text = infoWindowData.vicinity

        return view
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }
}