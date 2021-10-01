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
import androidx.navigation.fragment.findNavController
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.ui.base.BaseFragment
import com.cookandroid.happycup.databinding.FragmentMainBinding
import com.cookandroid.happycup.ui.main.view.activity.CaptureActivity
import com.cookandroid.happycup.ui.main.view.activity.MainActivity
import com.cookandroid.happycup.ui.main.view.dialog.MyCustomDialog
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
        binding.mapView.setPOIItemEventListener(eventListener)  // 마커 클릭 이벤트 리스너 등록
        binding.mapView.setZoomLevel(3, true)
        makeMarker("커핑 동천점",37.332255,127.102127)
        makeMarker("커핑 판교점",37.40290225113107,127.10664819647337)
        makeMarker("우리집", 37.23135,127.21004)
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
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun mapViewSetUp() {
        // 권한 승인 되어 있으면 Tracking
        if (checkLocationServicesStatus() &&
            requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "mapViewSetUp: 권한승인 ")
            startTracking()
        } else if (checkLocationServicesStatus()) {
            // GPS가 켜져있을 경우
            Log.d(TAG, "mapViewSetUp: gps on ")
            requestRuntimePermissions()
        } else {
            // GPS가 꺼져있을 경우
            Log.d(TAG, "mapViewSetUp: gps off ")
            showDialogForLocationServiceSetting()
        }
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
            val myCustomDialog = MyCustomDialog(context,lat,lng)
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
    }

}