package com.example.ecomap

import androidx.appcompat.app.AppCompatActivity
import net.daum.mf.map.api.MapPoint
/*
fun map_buttons(myparent: AppCompatActivity) {
    myparent
    binding.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location!!.latitude, location!!.longitude), false);

}
*/
/*
val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }
//below sets the center of map to current location
val manager=getSystemService(LOCATION_SERVICE) as LocationManager
if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
    val location: Location? = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    binding.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location!!.latitude, location!!.longitude), false);
}
 */