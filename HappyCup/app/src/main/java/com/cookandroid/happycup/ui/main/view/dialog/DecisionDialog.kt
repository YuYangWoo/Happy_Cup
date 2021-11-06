package com.cookandroid.happycup.ui.main.view.dialog

import android.content.Context
import com.cookandroid.gachon_study_room.ui.base.BaseDialog
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.DialogFailBinding

class DecisionDialog(context: Context, var kind: String) : BaseDialog<DialogFailBinding>(context, R.layout.dialog_fail) {

    override fun init() {
        super.init()
        when (kind) {
            "QRFail" -> { // 실패
                binding.imageView18.setImageResource(R.drawable.fail)
            }
            "returnSuccess" -> {
                binding.imageView18.setImageResource(R.drawable.cup_success)
            }
            "returnFail" -> {
                binding.imageView18.setImageResource(R.drawable.fail)
            }
        }
    }

}