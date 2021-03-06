package com.cookandroid.happycup.ui.main.view.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.MediaStore
import android.view.*
import com.cookandroid.happycup.ui.base.BaseDialogFragment
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.DialogKindBinding
import com.cookandroid.happycup.ui.main.view.fragment.MainFragment
import com.cookandroid.happycup.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class KindDialog : BaseDialogFragment<DialogKindBinding>(R.layout.dialog_kind) {

    private val viewModel: MainViewModel by sharedViewModel()
    override fun init() {
        super.init()
        btn()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize(this@KindDialog, 0.7f, 0.3f)
    }

    private fun btn() {
        binding.btnPaper.setOnClickListener {
            ExplainDialog("종이").show(requireActivity().supportFragmentManager, "explain")
            viewModel.getKind("paper")
            dismiss()
        }
        binding.btnPlastic.setOnClickListener {
            ExplainDialog("플라스틱").show(requireActivity().supportFragmentManager, "explain")
            viewModel.getKind("plastic")
            dismiss()
        }
    }
}