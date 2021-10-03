package com.cookandroid.happycup.ui.main.view.fragment

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.media.audiofx.BassBoost
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.ui.base.BaseFragment
import com.cookandroid.happycup.databinding.FragmentMainBinding
import com.cookandroid.happycup.ui.main.view.activity.CaptureActivity
import com.cookandroid.happycup.ui.main.view.activity.MainActivity
import com.cookandroid.happycup.ui.main.view.dialog.MyCustomDialog
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import kotlin.math.log


class MainFragment :
    BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val eventListener by lazy { MarkerEventListener(requireContext()) }   // 마커 클릭 이벤트 리스너

    override fun init() {
        super.init()
        mapViewSetUp()
        btnHamburger()
        btn()
        initMapView()
    }

    private fun initMapView() {
        requireActivity().findViewById<NavigationView>(R.id.navigation).getHeaderView(0).apply {
            findViewById<TextView>(R.id.btnShopB).setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToShopFragment())
                requireActivity().findViewById<DrawerLayout>(R.id.drawer).closeDrawer(requireActivity().findViewById<NavigationView>(R.id.navigation))
            }
        }

        binding.mapView.setPOIItemEventListener(eventListener)  // 마커 클릭 이벤트 리스너 등록
        binding.mapView.setZoomLevel(3, true)
        makeMarker("투썸 서울역점",37.5540998765133, 126.9656410165552)
        makeMarker("카페 0.25",37.554462671674564, 126.96645595261516)
        makeMarker("이디야 만리동고개점",37.55496449717344, 126.96398832039547)
        makeMarker("The house 1932",37.55535379391278, 126.96742941124468)
        makeMarker("Coffee Up",37.55456811921269, 126.96881087655738)
        makeMarker("383커피",37.5539252882969, 126.96869074913889)
        makeMarker("줄리아",37.55313959853627, 126.96511695843864)
        makeMarker("서계동 커피집",37.553168566340624, 126.96432290698624)
        makeMarker("서울역",37.55510783917329, 126.96983752855546)
        makeMarker("카페 오구오구",37.55753756041108, 126.9681125417674)
        makeMarker("할리스 충정래미안점",37.558775733788, 126.9661881128639)
        makeMarker("덕수궁",37.566617721600664, 126.97523825822653)
        makeMarker("서울광장",37.56603964290632, 126.97753288683423)
        makeMarker("카페뜨락",37.56386828286175, 126.97472241148527)
        makeMarker("시청역",37.56375548320803, 126.97543392423184)
        makeMarker("한옥 카페",37.561208246590006, 126.97698923991325)
        makeMarker("을지로역",37.565759904377394, 126.98230375949225)
        makeMarker("광화문역",37.57133663420827, 126.97651399101295)
        makeMarker("투썸플레이스 광화문점",37.57062726137044, 126.97418362309794)
        makeMarker("미국 대사관",37.572768743679795, 126.97832087045177)
        makeMarker("종각역",37.57030750945801, 126.98255197310739)
        makeMarker("웨스턴 조선호텔",37.564122437169395, 126.97968344410697)
        makeMarker("스탠다드차드은행",37.56089760774992, 126.98065368824346)
        makeMarker("신세계백화점",37.560506300959986, 126.98022814256956)
        makeMarker("투썸플레이스 명동점",37.56103683431224, 126.9825103670788)
        makeMarker("이디야 한국은행점",37.56153861553794, 126.97898058012109)
        makeMarker("카페 드 파리 명동2호점",37.56260170020556, 126.98374418323466)
        makeMarker("이디야 명동점",37.56311197545062, 126.98363689487729)
        makeMarker("인제대 서울백병원",37.56519934453298, 126.98785446837361)
        makeMarker("카페 드롭탑",37.569767484709864, 126.98220323244101)
        makeMarker("커피빈 광화문점",37.57000559068633, 126.97894166637674)
        makeMarker("할리스 무교점",37.56857694340969, 126.97945665049214)
        makeMarker("카페 이마",37.56986953024948, 126.97768639247764)
        makeMarker("폴바셋 코리아나호텔점",37.56844083993293, 126.97653840688773)
        makeMarker("카페서울아워",37.56700365850925, 126.97661350874819)
        makeMarker("카페 그레이스",37.56685908843077, 126.97618435531867)
        makeMarker("카페정담",37.56630250184627, 126.97284492603481)
        makeMarker("르풀",37.5665957506047, 126.97212555655175)
        makeMarker("서울 도서관",37.566358181054454, 126.97783068569738)
        makeMarker("IBK기업은행",37.56759808766125, 126.9795524784526)
        makeMarker("맥도날드",37.5540998765133, 126.9656410165552)
        makeMarker("롯데호텔 서울점",37.565731426106716, 126.98101696460506)
        makeMarker("롯데백화점",37.56466114286707, 126.98216308529328)
        makeMarker("스타벅스 을지로입구역점",37.565520080453375, 126.98346127445532)
        makeMarker("제주은행",37.56383621328592, 126.9845180648008)
        makeMarker("카페베네",37.56186314877985, 126.98759724076503)
        makeMarker("명동역 1번출구",37.56085108248358, 126.98708225663268)
        makeMarker("남산롯데캐슬아이리스",37.55813374405395, 126.98198605949479)
        makeMarker("씨네큐브 광화문점",37.56936829398569, 126.97238277141572)
        makeMarker("정동극장",37.56580784241997, 126.97287747628042)
    }

    private fun btn() {
        binding.btnQr.setOnClickListener {
            scanQRCode()
        }
        binding.btnShop.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToShopFragment())
        }
    }

    // QR Reader
    private fun scanQRCode() {
        val integrator = IntentIntegrator.forSupportFragment(this).apply {
            captureActivity = CaptureActivity::class.java // 가로 세로
            setOrientationLocked(false)
            setPrompt("Scan QR code")
            setBeepEnabled(false) // 소리 on off
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        }
        integrator.initiateScan()
    }

    // QR 결과
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                toast(requireContext(), "Cancelled")
            } else { // 스캔 되었을 때
                Log.d(TAG, result.contents)
                var intent = Intent().apply {
                    action = MediaStore.ACTION_IMAGE_CAPTURE
                }
                startActivityForResult(intent, TAKE_PICTURE)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

        if(requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToPointFragment())
        }
    }

    private fun mapViewSetUp() {
        // 권한 승인 되어 있으면 Tracking
        binding.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.56499776347082, 126.97753860438172), true);

        binding.btnGps.setOnClickListener {
            if (checkLocationServicesStatus() &&
                requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                mapStatus = if (!mapStatus) {
                    startTracking()
                    true
                } else {
                    startTracking2()
                    false
                }
            }
        }

    }

    private fun makeMarker(name : String, Latitude : Double, Longitude : Double) {
        val marker = MapPOIItem()
        marker.apply {
            itemName = name   // 마커 이름
            mapPoint = MapPoint.mapPointWithGeoCoord(Latitude, Longitude)   // 좌표
            markerType = MapPOIItem.MarkerType.CustomImage          // 마커 모양 (커스텀)
            customImageResourceId = R.drawable.marker           // 커스텀 마커 이미지
            selectedMarkerType = MapPOIItem.MarkerType.CustomImage  // 클릭 시 마커 모양 (커스텀)
            customSelectedImageResourceId = R.drawable.selected_marker      // 클릭 시 커스텀 마커 이미지
            isCustomImageAutoscale = false      // 커스텀 마커 이미지 크기 자동 조정
            //setCustomImageAnchor(0.5f, 1.0f)    // 마커 이미지 기준점
        }
        binding.mapView.addPOIItem(marker)
    }

    class MarkerEventListener(val context: Context): MapView.POIItemEventListener {
        // 마커 클릭시 메서드
        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            val lat: Double = poiItem?.getMapPoint()?.getMapPointGeoCoord()!!.latitude
            val lng: Double = poiItem?.getMapPoint()?.getMapPointGeoCoord()!!.longitude
            val name: String = poiItem?.getItemName()

            val myCustomDialog = MyCustomDialog(context,lat,lng,name)
            myCustomDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            myCustomDialog.show()

        }

        // 말풍선 생성 메서드
        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {

        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
            // 말풍선 클릭 시
        }

        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {
            // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
        }

    }

    private fun btnHamburger() {
        binding.btnHamburger.setOnClickListener {
            if ((activity as MainActivity).binding.drawer.isDrawerOpen(GravityCompat.START)) {
                (activity as MainActivity).binding.drawer.closeDrawer(GravityCompat.START)
            } else {
                (activity as MainActivity).binding.drawer.openDrawer(GravityCompat.START)
            }
        }
    }

    // 위치 권한 요청
    private fun requestRuntimePermissions() {
        if (requireActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startTracking()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    // 권한 요청 결과 받기
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isEmpty()) {
                    throw RuntimeException("권한이 비어있습니다.")
                }
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: 사용자가 승인했습니다.")
                    startTracking()
                } else {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Log.d(TAG, "사용자가 권한요청을 거부했습니다. 계속 요청하겠습니다.")
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSION_REQUEST_CODE
                        )

                    } else {
                        Log.d(TAG, "사용자가 거절했습니다.")
                        showDialogToGetPermission()
                    }
                }
            }
        }
    }

    // 다이얼로그로 권한 얻기
    private fun showDialogToGetPermission() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("권한 요청")
            .setMessage(
                "앱을 사용하기 위해서는 권한 요청이 필요합니다. " +
                        "OK를 누르면 권한 설정 창으로 이동합니다."
            )
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireActivity().packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        builder.setNegativeButton("Later") { dialogInterface, i ->
            // ignore
        }
        val dialog = builder.create()
        dialog.show()
    }

    // GPS 설정으로 이동
    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            """
                앱을 사용하기 위해서는 위치 서비스가 필요합니다.
                위치 서비스를 설정하시려면 설정을 눌러주세요
                """.trimIndent()
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { dialog, id ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }
        builder.setNegativeButton(
            "취소"
        ) { dialog, id -> dialog.cancel() }
        builder.create().show()
    }

    // 위치 서비스 상태확인
    private fun checkLocationServicesStatus(): Boolean {
        val locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    // 위치추적 시작
    private fun startTracking() {
        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }

    private fun startTracking2() {
        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
    }

    companion object {
        private const val GPS_ENABLE_REQUEST_CODE = 2001
        const val TAG = "Main_F"
        const val PERMISSION_REQUEST_CODE = 1001
        private var mapStatus = false
        const val TAKE_PICTURE = 1
    }

}