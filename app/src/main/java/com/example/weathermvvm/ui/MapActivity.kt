package com.example.weathermvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weathermvvm.MapUtility
import com.example.weathermvvm.R
import org.osmdroid.views.MapView

class MapActivity: AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps)

        mapView = findViewById(R.id.map_view)
        val mapUtility = MapUtility(mapView)
        mapUtility.setupMap()
    }

}