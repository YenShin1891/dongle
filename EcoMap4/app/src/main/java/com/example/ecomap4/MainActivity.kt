package com.example.ecomap4

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ecomap4.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MainActivity() : AppCompatActivity(), MapView.POIItemEventListener, MapView.CurrentLocationEventListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NavigationView>
    private lateinit var currentLocation: MapPoint


    //val sql = Mysqlfunctions()
    var locationList = mutableListOf<Triple<String, Double, Double>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val UserID= Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID) // context to this

        /*
        try {
            Amplify.configure(applicationContext)
            Log.d("AWSamplify", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.d("AWSamplify", "Could not initialize Amplify", error)
        }

        val button = findViewById<Button>(R.id.sample_button)
        button.setOnClickListener{
            showWithTransferUtility()
        }

         */

        //------get location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
                if (isGranted){
                    //I am happy
                }else{
                    val toast = Toast.makeText(this, "Denied permission: tracking service unavailable", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }


        //======instantiating map related objects======
        mapView = MapView(this)
        binding.mapViewLayout.addView(mapView)
        mapView.mapType=MapView.MapType.Hybrid
        mapView.setPOIItemEventListener(this)
        mapView.setCurrentLocationEventListener(this)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
        mapView.setShowCurrentLocationMarker(true)

        currentLocation= MapPoint.mapPointWithGeoCoord(36.372884, 127.363503)
        mapView.setMapCenterPoint(currentLocation, false)


        /*
        * ADD POI ITEMS
         */
        /*
        object : Thread() {
            override fun run() {
                Log.d("connection!", "in Thread")
                sql.getConnection()
            }
        }

        class SqlThread : Thread() {
            override fun run() {
                Log.d("connection!", "in Thread")
                sql.getConnection()
                locationList = sql.getPinLocation()
            }
        }
        val sqlThread = SqlThread()
        sqlThread.start()

        //val sql = Mysqlfunctions() //1
        //sql.getConnection() //2

         */

        //var locationList = sql.getPinLocation()  //3 error
        var markerList = mutableListOf<MapPOIItem>()
        for (i in locationList){
            val newMarker=addMapPOIItem(i.first, i.second, i.third)
            markerList.add(newMarker)
        }

        val firstMarker=addMapPOIItem("hehe", 36.372885, 127.363504)
        val secondMarker=addMapPOIItem("hihi", 36.372800, 127.363599)
        //var markerList = mutableListOf(firstMarker, secondMarker)



        /*
        * PIN PAGE - BOTTOM DRAWER
        * */
        bottomSheetBehavior = BottomSheetBehavior.from(binding.navigationView)
        binding.navigationView.inflateHeaderView(R.layout.full_navigation_drawer)

        bottomSheetBehavior.addBottomSheetCallback(object:BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) { }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.navigationView.getHeaderView(1).visibility = View.GONE
                        binding.navigationView.getHeaderView(0).visibility = View.VISIBLE
                        //binding.navigationView.removeHeaderView(binding.navigationView.getHeaderView(0))
                        //binding.navigationView.inflateHeaderView(R.layout.header_navigation_drawer)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        //if possible, show a middle drawer with only pin name

                        //Toast.makeText(applicationContext, "bottom sheet is dragging", Toast.LENGTH_SHORT).show()
                        //Log.d("headerCount", "before ${binding.navigationView.headerCount}")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.navigationView.getHeaderView(0).visibility = View.GONE
                        binding.navigationView.getHeaderView(1).visibility = View.VISIBLE
                        //binding.navigationView.removeHeaderView(binding.navigationView.getHeaderView(0))
                        //binding.navigationView.inflateHeaderView(R.layout.full_navigation_drawer)
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        binding.navigationView.getHeaderView(0).visibility = View.GONE
                        binding.navigationView.getHeaderView(1).visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> { }
                    BottomSheetBehavior.STATE_SETTLING -> { }
                }
            }
        }
        )
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.peekHeight = 700
        bottomSheetBehavior.isFitToContents = false //Now we can use HALF_EXPANDED
        bottomSheetBehavior.expandedOffset = 100
        bottomSheetBehavior.halfExpandedRatio = 0.75F


        /*
        * CURRENT LOCATION BUTTON
        * */
        //move map center to current location if we have permission, else display toast message
        binding.myLocationButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if (currentLocation != null){
                    mapView.setMapCenterPoint(currentLocation, true)
                }
                else{
                    val toast = Toast.makeText(this, "We don't know where you are, but we do have permission", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            else {
                val toast = Toast.makeText(this, "give me permission", Toast.LENGTH_SHORT)
                toast.show()
            }
        }


        /*
        * NEXT IMPLEMENTATION
        * */




    }


    /*
    private fun getConnection() {
        var stmt: Statement? = null
        var rs: ResultSet? = null
        var conn: Connection? = null
        val username = "dongledbadmin"
        val password = "dongledbadmin!@"

        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            /*
            var driver = DriverManager.getDriver("jdbc:" + "mysql" + "://" +
                    "tsclouddb.cn3e2kgsuevi.ap-northeast-2.rds.amazonaws.com" +
                    ":" + "3306" + "/")
            Log.d("driverconnection", "${driver}")
            Log.d("driverconnection", "${connectionProps}")
            */
            conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "tsclouddb.cn3e2kgsuevi.ap-northeast-2.rds.amazonaws.com" +
                        ":" + "3306" + "/" +
                        "",
                connectionProps
            )

        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
            Log.d("connection!", "sqlexception")
        }
        catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
            Log.d("connection!", "geunyang Exception")
        }
        Log.d("connection!", "${conn}")
    }
    */

    /*
    private fun showWithTransferUtility() {
        // Initialize the Amazon Cognito credentials provider
        val credentialsProvider = CognitoCachingCredentialsProvider(
            applicationContext,
            "ap-northeast-2:7f6c86c9-3c2a-41a5-93c3-a2e880bab0b2", // identity pool ID
            Regions.AP_NORTHEAST_2 // Region
        )

        TransferNetworkLossHandler.getInstance(applicationContext)

        val transferUtility = TransferUtility.builder()
            .context(applicationContext)
            .defaultBucket("dongle2021awsbuket")
            .s3Client(AmazonS3Client(credentialsProvider, Region.getRegion(Regions.AP_NORTHEAST_2)))
            .build()


        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "downloaded",".jpg",
            storageDir
        )


        //val filePath = file.absolutePath

        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.example.ecomap4.fileprovider", file
        )
        Log.d("URI", photoURI.toString())


        val downloadObserver = transferUtility.download("dongle2021awsbuket", "main.JPG", file)

        //val downloadObserver = transferUtility.download("main.JPG", File(filePath))

        //val downloadObserver = transferUtility.download("main.JPG", File(storageDir!!.absolutePath + "/main.JPG"))



        downloadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    Log.d("AWS", "DOWNLOAD Completed!")
                }
            }

            override fun onProgressChanged(id: Int, current: Long, total: Long) {
                try {
                    val done = (((current.toDouble() / total) * 100.0).toInt()) //as Int
                    Log.d("AWS", "DOWNLOAD - - ID: $id, percent done = $done")
                } catch (e: Exception) {
                    Log.d("AWS", "Trouble calculating progress percent", e)
                }
            }

            override fun onError(id: Int, ex: Exception) {
                Log.d("AWS", "DOWNLOAD ERROR - - ID: $id - - EX: ${ex.message.toString()}")
            }


        })
    }
     */

    //======from below sang seong ei 's space======
    //======from below sang seong ei 's space======
    private fun addMapPOIItem(idInput: String, lat: Double, lon: Double): MapPOIItem{
        val pin = MapPOIItem()
        pin.itemName=idInput//this property is essential for the pin to be visible. Let's store dbId here, and not use MMapPOIItem class
        pin.mapPoint = MapPoint.mapPointWithGeoCoord(lat, lon)
        pin.markerType = MapPOIItem.MarkerType.BluePin
        pin.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(pin)
        return pin
    }

    //Member methods of POIItemEventListener
    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {}
    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?, p2: MapPOIItem.CalloutBalloonButtonType?) {}
    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {}

    //Member methods of CurrentLocationEventListener
    override fun onCurrentLocationUpdate(mapView : MapView, currentLocationInListener : MapPoint, accuracyInMeters : Float){
        currentLocation=currentLocationInListener
    }
    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {}
    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
        Log.d("Location", "currentLocation update failed")
    }
    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {}
}
