package com.cookandroid.happycup.ui.main.view.dialog

import android.content.Context
import android.os.Bundle
import com.cookandroid.gachon_study_room.ui.base.BaseDialog
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.DialogQrMakeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.lang.Exception

class QRDialog(context: Context) : BaseDialog<DialogQrMakeBinding>(context,R.layout.dialog_qr_make) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 사용가능 유무
        val string = "cupping:true, count:1"
        val multiFormatWriter = MultiFormatWriter()
        try {
            var bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE,200,200);
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            binding.qr.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}