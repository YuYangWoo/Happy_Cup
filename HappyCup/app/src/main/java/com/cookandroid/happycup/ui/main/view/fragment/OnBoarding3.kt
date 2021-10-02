package com.cookandroid.happycup.ui.main.view.fragment

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.Guideline
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.databinding.FragmentOnboarding3Binding
import com.cookandroid.happycup.ui.base.BaseFragment
import com.google.android.material.textfield.TextInputLayout

class OnBoarding3 : BaseFragment<FragmentOnboarding3Binding>(R.layout.fragment_onboarding3) {

    override fun init() {
        super.init()
        onBoarding()
        binding.btnLogin1.setOnClickListener {
            binding.cstLyGuide.visibility = View.GONE
            binding.btnLogin1.visibility = View.GONE
            binding.imageView6.visibility = View.GONE
            requireActivity().findViewById<CheckBox>(R.id.checkBox).visibility = View.VISIBLE
            requireActivity().findViewById<TextInputLayout>(R.id.edtId).visibility = View.VISIBLE
            requireActivity().findViewById<TextInputLayout>(R.id.edtPassword).visibility = View.VISIBLE
            requireActivity().findViewById<ImageView>(R.id.imageView).visibility = View.VISIBLE
            requireActivity().findViewById<ImageView>(R.id.imageView3).visibility = View.VISIBLE
            requireActivity().findViewById<Guideline>(R.id.guide2).visibility = View.VISIBLE
            requireActivity().findViewById<Guideline>(R.id.cstGuide).visibility = View.VISIBLE
            requireActivity().findViewById<Button>(R.id.btnLogin).visibility = View.VISIBLE
        }
    }

    private fun onBoarding() {
        MySharedPreferences.setOnBoarding(requireContext(), true)
    }

    companion object {
        fun newInstance() = OnBoarding3()
    }
}