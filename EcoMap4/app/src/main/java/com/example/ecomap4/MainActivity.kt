package com.example.ecomap4

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    private var selectedPinNo=0
    private lateinit var selectedPin: Pin
    private lateinit var halfPinInfoWindow: View
    private lateinit var fullPinInfoWindow: View
    var pinInfoList = mutableListOf<Pin>()
    //val sql = Mysqlfunctions()

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


        /**
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

        var pin1 = Pin("라일락", "Syringa vulgaris", 36.3739047, 127.3605267, "https://terms.naver.com/entry.naver?docId=2428094&cid=46694&categoryId=4669", arrayOf("향기로운", "봄"))
        pin1.pic_array[4] = arrayOf(R.drawable.n001_01_2, R.drawable.n001_01_1)
        pin1.memo_array[4] = arrayOf("", "오늘 날씨가 너무 맑았는데 꽃이 흰 꽃, 분홍색 꽃 등 여러 색깔을 관찰할 수 있었습니다. 라일락 향기를 평소에 좋아했는데 꽃의 생김새를 알 수 있어서 의미있는 관찰이었습니다.")
        pin1.pic_array[9] = arrayOf(R.drawable.n001_02_1, R.drawable.n001_02_2)
        pin1.memo_array[9] = arrayOf("", "라일락 꽃이 사라지니 잎이 괜히 더 커 보이네요")
        pinInfoList.add(pin1)

        var pin2 = Pin("별목련", "magnolia stellata", 36.3748103, 127.3594976, "https://terms.naver.com/entry.naver?docId=1214083&cid=40942&categoryId=32708", arrayOf("희귀한", "봄"))
        pin2.pic_array[3] = arrayOf(R.drawable.n060_01_1, R.drawable.n060_02_1)
        pin2.memo_array[3] = arrayOf("이제 꽃이 막 피기 시작하네요! 다 피면 더 예쁠 것 같아요 :)", "일주일 만에 꽃이 활짝 폈네요 :)")
        pin2.pic_array[9] = arrayOf(R.drawable.n060_03_1, R.drawable.n60_03_2)
        pin2.memo_array[9] = arrayOf("", "꽃이 없으니 목련인지 알아볼수가 없네요")
        pinInfoList.add(pin2)

        var pin3 = Pin("칠엽수", "Aesculus turbinata", 36.3705, 127.3605, "https://terms.naver.com/entry.naver?docId=1148308&cid=40942&categoryId=32699", arrayOf("열매", "봄", "가을"))
        pin3.pic_array[10] = arrayOf(R.drawable.e064_01, R.drawable.e064_02, R.drawable.e064_03)
        pin3.memo_array[10] = arrayOf("", "칠엽수의 열매와 잎을 자세히 보고싶다면?")
        pinInfoList.add(pin3)

        var pin4 = Pin("장미", "Rosa", 36.373555, 127.364035, "https://terms.naver.com/entry.naver?docId=769534&cid=46694&categoryId=46694", arrayOf("향기로운", "사진명소", "화려한"))
        pin4.pic_array[6] = arrayOf( R.drawable.n033_01_1, R.drawable.n033_01_2)
        pin4.memo_array[10] = arrayOf("", "장미가 마치 꽃다발처럼 피었어요!")
        pinInfoList.add(pin4)

        var pin5 = Pin("단풍나무", "Acer palmatum Thunb", 36.3691, 127.3625, "https://terms.naver.com/entry.naver?docId=5782203&cid=62861&categoryId=62861", arrayOf("가을", "화려한"))
        pin5.pic_array[6] = arrayOf( R.drawable.e023_01_1)
        pin5.memo_array[6] = arrayOf("아직 물들기 전 단풍입니다")
        pin5.pic_array[10] = arrayOf(R.drawable.e023_02_1, R.drawable.e023_02_2, R.drawable.e023_02_3)
        pin5.memo_array[10] = arrayOf("","", "슬슬 가을이 다가오면 빨간색으로 물들겠죠?")
        pinInfoList.add(pin5)

        var pin6 = Pin("반송", "umbrella pine", 36.371519, 127.361277, "https://terms.naver.com/entry.naver?docId=5782195&cid=62861&categoryId=62861", arrayOf("침엽수", "겨울"))
        pin6.pic_array[10] = arrayOf( R.drawable.e034_01_1, R.drawable.e034_01_2, R.drawable.e034_01_3)
        pin6.memo_array[10] = arrayOf("","파란색 하늘이랑 너무 잘 어우러지는 소나무에요.", "반송을 가까이서 찍어보았어요")
        pinInfoList.add(pin6)

        var pin7 = Pin("감나무", "Diospyros kaki", 36.36776972, 127.357695,
            "https://terms.naver.com/entry.naver?docId=1056396&cid=40942&categoryId=32692",
            arrayOf("가을", "열매"))
        pin7.pic_array[11] = arrayOf( R.drawable.w_001, R.drawable.w_002,R.drawable.w_003)
        pin7.memo_array[11] = arrayOf("","", "가을이라 주렁주렁 열린 감을 기대했는데 없어서 아쉬웠습니다.")
        pinInfoList.add(pin7)

        var pin9 = Pin("마", "Dioscorea batatas", 36.36759, 127.3586,
            "https://terms.naver.com/entry.naver?docId=1090070&cid=40942&categoryId=32816",
            arrayOf("가을", "뿌리채소"))
        pin9.pic_array[11] = arrayOf(R.drawable.w_007, R.drawable.w_008,R.drawable.w_009)
        pin9.memo_array[11] = arrayOf("","", "땅 파보면 진짜 마 있는거에요?")
        pinInfoList.add(pin9)

        var pin10 = Pin("스트로브잣나무", "Pinus strobus", 36.36835259, 127.35761709,
            "https://terms.naver.com/entry.naver?docId=1117287&cid=40942&categoryId=32685",
            arrayOf("침엽수"))
        pin10.pic_array[11] = arrayOf(R.drawable.w_010, R.drawable.w_011,R.drawable.w_012)
        pin10.memo_array[11] = arrayOf("","", "왜 영문 이름이 white pine인건가요? 그건 그렇고 침엽수인데도 잎이 말라서 갈색이 되었어요")
        pinInfoList.add(pin10)

        var pin11 = Pin("백합나무", "Liriodendron tulipifera L.", 36.3679494, 127.3577242,
            "https://terms.naver.com/entry.naver?docId=3540207&cid=46694&categoryId=46694",
            arrayOf("여름"))
        pin11.pic_array[11] = arrayOf(R.drawable.w_013, R.drawable.w_014,R.drawable.w_015)
        pin11.memo_array[11] = arrayOf("","", "단풍이 들어가는 모습이 예쁘네요…")
        pinInfoList.add(pin11)

        var pin12 = Pin("산사나무", "Crataegus pinnatifida", 36.37061388888889, 127.36012222222222,
            "https://terms.naver.com/entry.naver?docId=1108500&cid=40942&categoryId=32816",
            arrayOf("열매"))
        pin12.pic_array[10] = arrayOf(R.drawable.e069_01, R.drawable.e069_02, R.drawable.e069_03)
        pin12.memo_array[10] = arrayOf("","", "열매의 점들이 신기하여 지나가는길에 찍어보았어요")
        pinInfoList.add(pin12)

        var pin13 = Pin("산수유나무", "Cornus officinalis", 36.37038055555528, 127.36044999997222,
            "https://terms.naver.com/entry.naver?docId=1211087&cid=40942&categoryId=32711",
            arrayOf("열매", "겨울"))
        pin13.pic_array[10] = arrayOf(R.drawable.e070_01, R.drawable.e070_02)
        pin13.memo_array[10] = arrayOf("","", "바로 옆 산사나무 열매와는 다르게 표면이 매끈한 열매를 가지고 있네요")
        pinInfoList.add(pin13)

        var pin14 = Pin("백송", "Pinus bungeana", 36.37035833333056, 127.36065555527777,
            "https://terms.naver.com/entry.naver?docId=1100185&cid=40942&categoryId=32685",
            arrayOf("침엽수"))
        pin14.pic_array[9] = arrayOf(R.drawable.e072_01, R.drawable.e072_02)
        pin14.memo_array[9] = arrayOf("", "백송의 잎은 다른 소나무에 비해 덜 거친것 같아보이네요")
        pinInfoList.add(pin14)
//        var pinInfos=arrayOf(
//            arrayOf("에베베베", "학명", 36.372885, 127.363504, "url", arrayOf("예쁜", "안예쁜"),
//                arrayOf(1, "uri1", "uri2", "uri3"),
//                arrayOf("오우 이 나무 참 예브네요","오늘 날시가 참 좋아요","13인의아해가질주를한다")),
//
//            arrayOf("엘렐렐레", "학명없다", 36.372800, 127.363599, "url", arrayOf("예쁜", "안예쁜"),
//                arrayOf(1, "uri1", "uri2", "uri3"),
//                arrayOf("오우 이 나무 참 예브네요","오늘 날시가 참 좋아요","13인의아해가질주를한다"))
//        )
        for (i in pinInfoList.indices){
            val pin= pinInfoList[i]
            markerList.add(addMapPOIItem(i.toString(), pin.longi, pin.lati))
        }

        /*
        * PIN PAGE - BOTTOM DRAWER
        * */
        bottomSheetBehavior = BottomSheetBehavior.from(binding.navigationView)
        halfPinInfoWindow=binding.navigationView.getHeaderView(0)
        fullPinInfoWindow=binding.navigationView.inflateHeaderView(R.layout.full_navigation_drawer)
        var card1month=fullPinInfoWindow.findViewById<com.google.android.material.card.MaterialCardView>(R.id.full_pin_info_card_month1)
        card1month.setOnClickListener{
            //Toast.makeText(applicationContext, "full window clicked", Toast.LENGTH_SHORT).show()
            //card1month1img.setImageResource(R.drawable.tree3)

            val bannerIntent: Intent = Intent(this, ManagePic::class.java)
            startActivity(bannerIntent)
        }

        var wiki_url = fullPinInfoWindow.findViewById<TextView>(R.id.url_wiki)
        wiki_url.setOnClickListener {
            val wikiIntent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://terms.naver.com/entry.naver?docId=768292&cid=46694&categoryId=46694"))
            Intent()
            startActivity(wikiIntent)
        }

        bottomSheetBehavior.addBottomSheetCallback(object:BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) { }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        halfPinInfoWindow.visibility = View.VISIBLE
                        fullPinInfoWindow.visibility = View.GONE
                        //binding.navigationView.removeHeaderView(binding.navigationView.getHeaderView(0))
                        //binding.navigationView.inflateHeaderView(R.layout.header_navigation_drawer)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        //if possible, show a middle drawer with only pin name
                        //Toast.makeText(applicationContext, "bottom sheet is dragging", Toast.LENGTH_SHORT).show()
                        //Log.d("headerCount", "before ${binding.navigationView.headerCount}")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        halfPinInfoWindow.visibility = View.GONE
                        fullPinInfoWindow.visibility = View.VISIBLE
                        //binding.navigationView.removeHeaderView(binding.navigationView.getHeaderView(0))
                        //binding.navigationView.inflateHeaderView(R.layout.full_navigation_drawer)
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        halfPinInfoWindow.visibility = View.GONE
                        fullPinInfoWindow.visibility = View.VISIBLE
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
        pin.isShowCalloutBalloonOnTouch=false
        mapView.addPOIItem(pin)
        return pin
    }

    //Member methods of POIItemEventListener
    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        selectedPinNo = p1!!.itemName.toInt()
        selectedPin = pinInfoList[selectedPinNo]
        halfPinInfoWindow.findViewById<TextView>(R.id.pin_collapsed_window_title).text=selectedPin.pin_name
        halfPinInfoWindow.findViewById<TextView>(R.id.pin_collapsed_window_description).text=selectedPin.scientific_name
        var j=0
        for (i in 1 until 13){
            if (!(selectedPin.pic_array[i].isEmpty())){
                for (pics in selectedPin.pic_array[i]){
                    if (j==0){
                        halfPinInfoWindow.findViewById<ImageView>(R.id.thumbnail1).setImageResource(pics)
                    }else if (j==1){
                        halfPinInfoWindow.findViewById<ImageView>(R.id.thumbnail2).setImageResource(pics)
                    }else if (j==2){
                        halfPinInfoWindow.findViewById<ImageView>(R.id.thumbnail3).setImageResource(pics)
                    }
                    j+=1
                }
            }
        }

        fullPinInfoWindow.findViewById<TextView>(R.id.pin_full_window_title).text=selectedPin.pin_name
        fullPinInfoWindow.findViewById<TextView>(R.id.pin_full_window_description).text=selectedPin.scientific_name
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

/**
 * Data Class
 */
class Pin(
    var pin_name: String,
    var scientific_name: String,
    var longi: Double = 36.372885,
    var lati: Double = 127.363504,
    var wiki_url: String,
    var keywords: Array<String>
    ) {
    var pic_array: Array<Array<Int>> = Array(13){emptyArray()}
    var memo_array: Array<Array<String>> = Array(13){emptyArray()}
}







