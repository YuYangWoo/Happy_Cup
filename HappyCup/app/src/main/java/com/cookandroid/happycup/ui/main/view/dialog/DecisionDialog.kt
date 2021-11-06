package com.cookandroid.happycup.ui.main.view.dialog

import android.content.Context
import android.util.Log
import androidx.navigation.fragment.navArgs
import com.cookandroid.gachon_study_room.ui.base.BaseDialog
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.DialogFailBinding
import com.cookandroid.happycup.ui.base.BaseDialogFragment
import com.cookandroid.happycup.ui.base.BaseFragment

class DecisionDialog : BaseFragment<DialogFailBinding>(R.layout.dialog_fail) {
    private val args: DecisionDialogArgs by navArgs()
    private val TAG = "DecisionDialog"
    override fun init() {
        super.init()
        Log.d(TAG, "init: ${args.kind}")
        when (args.kind) {
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