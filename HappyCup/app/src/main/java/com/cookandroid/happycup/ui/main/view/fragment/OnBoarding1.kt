package com.cookandroid.happycup.ui.main.view.fragment

import android.content.Context
import androidx.viewpager2.widget.ViewPager2
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.databinding.FragmentOnboarding1Binding
import com.cookandroid.happycup.ui.adapter.ViewPagerAdapter
import com.cookandroid.happycup.ui.base.BaseFragment
import java.lang.reflect.Array.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance

class OnBoarding1 : BaseFragment<FragmentOnboarding1Binding>(R.layout.fragment_onboarding1) {

    override fun init() {
        super.init()
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        binding.btnNext.setOnClickListener {
            viewPager!!.currentItem = 1
        }
    }
    companion object {
        fun newInstance() = OnBoarding1()
    }


}