package com.cookandroid.happycup.ui.main.view.dialog

import android.content.Context
import android.util.Log
import androidx.navigation.fragment.navArgs
import com.cookandroid.gachon_study_room.ui.base.BaseDialog
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.DialogFailBinding
import com.cookandroid.happycup.ui.base.BaseDialogFragment
import com.cookandroid.happycup.ui.base.BaseFragment
import kotlinx.coroutines.*

class DecisionDialog : BaseFragment<DialogFailBinding>(R.layout.dialog_fail) {
    private val args: DecisionDialogArgs by navArgs()
    private val TAG = "DecisionDialog"
    override fun init() {
        super.init()
        Log.d(TAG, "init: ${args.kind}")
        when (args.kind) {
            "QRFail" -> { // 실패
                binding.imageView18.background = resources.getDrawable(R.drawable.fail, null)
                backStack()
            }
            "returnSuccess" -> {
                binding.imageView18.background = resources.getDrawable(R.drawable.cup_success, null)
            }
            "returnFail" -> {
                binding.imageView18.background = resources.getDrawable(R.drawable.fail, null)
                backStack()
            }
        }




    }

    private fun backStack() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500L)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}