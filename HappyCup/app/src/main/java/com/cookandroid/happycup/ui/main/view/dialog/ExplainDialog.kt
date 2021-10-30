package com.cookandroid.happycup.ui.main.view.dialog

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.databinding.DialogExplainBinding
import com.cookandroid.happycup.ui.base.BaseDialogFragment
import com.cookandroid.happycup.ui.main.view.fragment.MainFragment.Companion.TAKE_PICTURE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExplainDialog(var kind: String) :
    BaseDialogFragment<DialogExplainBinding>(R.layout.dialog_explain) {

    override fun init() {
        super.init()
        img()
        nextScreen()
        btn()
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val extra = intent!!.extras
                val imageBitmap = extra!!.get("data") // bitmap
            }
        }

    private fun btn() {
        binding.chkBox.setOnClickListener {
            MySharedPreferences.setExplain(requireContext(), true)
        }
    }

    private fun img() {
        if (kind == "종이") {
            binding.imgContent.setImageResource(R.drawable.img_paper)
        } else {
            binding.imgContent.setImageResource(R.drawable.img_plastic)
        }
    }


    private fun nextScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            var intent = Intent().apply {
                action = MediaStore.ACTION_IMAGE_CAPTURE
            }
            if (MySharedPreferences.getExplain(requireContext())) {
                startForResult.launch(intent)
            } else {
                delay(2000L)
                startForResult.launch(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize(this@ExplainDialog, 0.85f, 0.85f)
    }
}