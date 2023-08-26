package com.example.weathermvvm

import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapUtility(private val mapView: MapView) {

    fun setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.controller.setZoom(10.0)
        mapView.controller.setCenter(GeoPoint(50.431759, 30.517540))

        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

    }
}