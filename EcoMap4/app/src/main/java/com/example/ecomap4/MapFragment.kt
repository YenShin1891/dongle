package com.example.ecomap4

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
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
                    //so sad
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
        val mapViewContainer = binding.mapView as ViewGroup
        mapViewContainer.addView(mapView)

        //experimental pin at seoul station
        val marker = MapPOIItem()
        marker.itemName = "Default Marker"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(37.555923849029526, 126.97232388116235)

        //need to designate two forms: not clicked, and when clicked. currently in default form
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker)

        //move map center to current location if we have permission, else display toast message
        binding.myLocationButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                val manager = requireContext().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
                val location: Location? = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location!!.latitude, location!!.longitude), false);
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