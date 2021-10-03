package com.cookandroid.happycup.ui.main.view.fragment

import androidx.navigation.fragment.findNavController
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.FragmentAiBinding
import com.cookandroid.happycup.ui.base.BaseFragment
import com.davemorrissey.labs.subscaleview.ImageSource

class AIFragment : BaseFragment<FragmentAiBinding>(R.layout.fragment_ai) {
    override fun init() {
        super.init()
        binding.aiImage.setImage(ImageSource.resource(R.drawable.ai))
        binding.btnCupping.setOnClickListener { findNavController().navigate(AIFragmentDirections.actionAIFragmentToMainFragment()) }
    }
}