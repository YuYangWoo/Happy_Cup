package com.cookandroid.happycup.ui.main.view.fragment

import androidx.core.view.GravityCompat
import com.cookandroid.gachon_study_room.ui.base.BaseFragment
import com.cookandroid.happycup.databinding.FragmentMainBinding
import com.cookandroid.happycup.ui.main.view.activity.MainActivity
import net.daum.mf.map.api.MapView


class MainFragment :
    BaseFragment<FragmentMainBinding>(com.cookandroid.happycup.R.layout.fragment_main) {

    override fun init() {
        super.init()
        val mapView = MapView(requireActivity())
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)

        binding.btnHamburger.setOnClickListener {
            if ((activity as MainActivity).binding.drawer.isDrawerOpen(GravityCompat.START)) {
                (activity as MainActivity).binding.drawer.closeDrawer(GravityCompat.START)
            } else {
                (activity as MainActivity).binding.drawer.openDrawer(GravityCompat.START)
            }

        }
    }

}