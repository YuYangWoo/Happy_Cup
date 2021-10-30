package com.cookandroid.happycup.ui.main.view.dialog

import android.content.Intent
import android.provider.MediaStore
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
            if(MySharedPreferences.getExplain(requireContext())) {
                var intent = Intent().apply {
                    action = MediaStore.ACTION_IMAGE_CAPTURE
                }
                startActivityForResult(intent, TAKE_PICTURE)
            } else {
                delay(2000L)
                var intent = Intent().apply {
                    action = MediaStore.ACTION_IMAGE_CAPTURE
                }
                startActivityForResult(intent, TAKE_PICTURE)
            }

            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize(this@ExplainDialog, 0.85f, 0.85f)
    }
}