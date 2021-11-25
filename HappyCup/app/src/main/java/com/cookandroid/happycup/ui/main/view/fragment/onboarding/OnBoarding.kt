package com.cookandroid.happycup.ui.main.view.fragment.onboarding

import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.FragmentOnboardingBinding
import com.cookandroid.happycup.ui.adapter.ViewPagerAdapter
import com.cookandroid.happycup.ui.base.BaseFragment

class OnBoarding : BaseFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {

    override fun init() {
        super.init()
        setupViewPager()
    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf(
            OnBoarding1.newInstance(), OnBoarding2.newInstance(), OnBoarding3.newInstance()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        //2
//        binding.viewPager.isUserInputEnabled = false
    }

}