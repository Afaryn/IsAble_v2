package com.example.isable_capstone.ui.maps

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.isable_capstone.R
import com.example.isable_capstone.api.ApiConfig
import com.example.isable_capstone.api.response.MapsResponseItem
import com.example.isable_capstone.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Map"

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val customMarker = CustomMarker(this)
        mMap.setInfoWindowAdapter(customMarker)

        val apiService = ApiConfig().apiService

        apiService.getMapsData().enqueue(object : Callback<List<MapsResponseItem>> {
            override fun onResponse(call: Call<List<MapsResponseItem>>, response: Response<List<MapsResponseItem>>) {
                if (response.isSuccessful) {
                    val mapsData = response.body()

                    mapsData?.let {
                        for (result in it) {
                            val marker = mMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(result.latitude?.toDouble() ?: 0.0, result.longtitude?.toDouble() ?: 0.0))
                                    .title(result.namatempat)
                                    .snippet(result.alamat)
                            )
                            marker?.tag = result
                        }
                        if (it.isNotEmpty()) {
                            val firstLocation = it[0]
                            val firstMarker = LatLng(firstLocation.latitude?.toDouble() ?: 0.0, firstLocation.longtitude?.toDouble() ?: 0.0)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(firstMarker, 15f))
                        }
                    } ?: run {
                        // Handle case where response body is null
                        Log.e("API Error", "body is null")
                    }
                } else {
                    Log.e("API Error", "Code: ${response.code()}, Message: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<MapsResponseItem>>, t: Throwable) {
                Log.e("Network Error", "Error: ${t.message}", t)
            }
        })

        getMyLocation()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}