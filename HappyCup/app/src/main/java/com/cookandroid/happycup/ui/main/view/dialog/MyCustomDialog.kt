package com.cookandroid.happycup.ui.main.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.CustomDialogBinding
import com.cookandroid.happycup.databinding.DialogListBinding
import com.google.android.gms.location.*

class MyCustomDialog(context: Context, lat: Double, lng: Double) : Dialog(context) {
    val TAG: String = "로그"
    val lat = lat
    val lng = lng
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest // 위치 요청
    lateinit var locationCallback: MyLocationCallBack // 내부 클래스, 위치 변경 후 지도에 표시.
    val REQUEST_ACCESS_FINE_LOCATION = 1000
    private lateinit var binding: CustomDialogBinding
    private lateinit var bindingTwo: DialogListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.custom_dialog,
            null,
            false
        )

        window!!.setGravity(Gravity.TOP) // 팝업창 위로 배치

        window!!.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // 어플이 사용되는 동안 화면 끄지 않기.
        binding.declarationBtn.setOnClickListener {

            bindingTwo = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_list,
                null,
                false
            )
            bindingTwo.dialBtn0.setOnClickListener {
                setContentView(R.layout.dialog_alert)
                window!!.setGravity(Gravity.CENTER)
            }
            bindingTwo.dialBtn1.setOnClickListener {
                setContentView(R.layout.dialog_alert)
                window!!.setGravity(Gravity.CENTER)
            }
            bindingTwo.dialBtn2.setOnClickListener {
                setContentView(R.layout.dialog_alert)
                window!!.setGravity(Gravity.CENTER)
            }
            bindingTwo.dialBtn3.setOnClickListener {
                setContentView(R.layout.dialog_alert)
                window!!.setGravity(Gravity.CENTER)
            }

            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            window!!.setGravity(Gravity.BOTTOM) // 팝업창 위로 배치
        }

        binding.findBtn.setOnClickListener {

            fusedLocationProviderClient = FusedLocationProviderClient(context)
            // 현재 사용자 위치를 저장.
            locationCallback = MyLocationCallBack() // 내부 클래스 조작용 객체 생성
            locationRequest = LocationRequest() // 위치 요청.

            locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            // 위치 요청의 우선순위 = 높은 정확도 우선.
            //locationRequest.interval = 10000 // 내 위치 지도 전달 간격
            //locationRequest.fastestInterval = 5000 // 지도 갱신 간격.

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )

            MyLocationCallBack()
        }
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)

            val location = p0?.lastLocation
            // 위도 경도를 지도 서버에 전달하면,
            // 위치에 대한 지도 결과를 받아와서 저장.

            location?.run {
                //val latLng = LatLng(latitude, longitude) // 위도 경도 좌표 전달.

                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("kakaomap://route?sp=$latitude,$longitude&ep=$lat,$lng&by=FOOT")
                );

                startActivity(context, intent, null)

                Toast.makeText(context, "$latitude, $longitude", Toast.LENGTH_SHORT).show()

            }
        }
    }
}

/* 배경 투명
window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
 */

/* 커스텀 다이얼로그 클릭 이벤트 설정
val dialog = CustomDialog()
// 버튼 클릭 이벤트 설정
dialog.setButtonClickListener(object: CustomDialog.OnButtonClickListener{

    override fun onButton1Clicked() {
        Toast.makeText(context, "감사합니다", Toast.LENGTH_SHORT).show()
    }

    override fun onButton2Clicked() {
        Toast.makeText(context, "조금 더 노력하겠습니다.", Toast.LENGTH_SHORT).show()

    }
    override fun onButton3Clicked() {
        Toast.makeText(context, "버튼3", Toast.LENGTH_SHORT).show()
    }
})

*/

/* 표준 다이얼로그
val builder = AlertDialog.Builder(context)
val itemList = arrayOf("회수기 고장", "지저분한 주변 환경", "회수기 컵이 꽉 참", "기타 사유")
builder.setTitle("신고 사유 선택")
builder.setCancelable(true)

builder.setItems(itemList) { dialog, which ->
    when(which) {
        0 -> Toast.makeText(context, "회수기 고장", Toast.LENGTH_SHORT).show()
        1 -> Toast.makeText(context, "지저분한 주변 환경", Toast.LENGTH_SHORT).show()
        2 -> Toast.makeText(context, "회수기 컵이 꽉 참", Toast.LENGTH_SHORT).show()
        4 -> Toast.makeText(context, "기타 사유", Toast.LENGTH_SHORT).show()
    }
}

val alertDialog = builder.create()
val window = alertDialog.window
window?.setGravity(Gravity.BOTTOM)
alertDialog.show()

*/