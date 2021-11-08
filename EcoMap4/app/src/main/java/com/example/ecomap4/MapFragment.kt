package com.example.ecomap4

import android.Manifest
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.ecomap4.databinding.FragmentMapBinding
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapView
import net.daum.mf.map.n.api.internal.NativeMapLocationManager.setCurrentLocationTrackingMode


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
                if (isGranted){
                    //I am happy
                }else{
                    val toast = Toast.makeText(context, "Denied permission: tracking service unavailable", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapView = MapView(context)
        mapView.mapType=MapView.MapType.Hybrid
        val mapViewContainer = binding.mapView as ViewGroup
        var currentLocation: MapPoint = MapPoint.mapPointWithGeoCoord(36.372884, 127.363503)
        mapView.setMapCenterPoint(currentLocation, false)

        mapViewContainer.addView(mapView)

        //initiating markers
        val marker = MapPOIItem()
        marker.itemName = "Default Marker"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(36.372884, 127.363503)

        //need to designate two forms: not clicked, and when clicked. currently in default form
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker)


        //track current location, store it at currentLocation
        var locationListener: MapView.CurrentLocationEventListener = object : MapView.CurrentLocationEventListener{
            override fun onCurrentLocationUpdate(mapView : MapView, currentLocationInListener : MapPoint, accuracyInMeters : Float){
                currentLocation=currentLocationInListener
                Log.d("Location", "currentLocation updated")
            }
            override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
            }
            override fun onCurrentLocationUpdateFailed(p0: MapView?) {
                Log.d("Location", "currentLocation update failed")
            }
            override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
            }
        }
        mapView.setCurrentLocationEventListener(locationListener)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
        mapView.setShowCurrentLocationMarker(true)

        //move map center to current location if we have permission, else display toast message
        binding.myLocationButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if (currentLocation != null){
                    mapView.setMapCenterPoint(currentLocation, true)
                }
                else{
                    val toast = Toast.makeText(this.context, "We don't know where you are, but we do have permission", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            else{
                val toast = Toast.makeText(this.context, "give me permission", Toast.LENGTH_SHORT)
                toast.show()
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}