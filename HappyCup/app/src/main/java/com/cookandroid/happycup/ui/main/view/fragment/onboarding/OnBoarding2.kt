package com.cookandroid.happycup.ui.main.view.fragment.onboarding

import androidx.viewpager2.widget.ViewPager2
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.FragmentOnboarding2Binding
import com.cookandroid.happycup.ui.base.BaseFragment

class OnBoarding2 : BaseFragment<FragmentOnboarding2Binding>(R.layout.fragment_onboarding2) {

    override fun init() {
        super.init()
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        binding.btnNext.setOnClickListener {
            viewPager!!.currentItem = 2
        }
    }
    companion object {
        fun newInstance() = OnBoarding2()
    }
}