package com.cookandroid.happycup.ui.main.view.fragment

import com.cookandroid.gachon_study_room.ui.base.BaseFragment
import com.cookandroid.happycup.databinding.FragmentMainBinding
import net.daum.mf.map.api.MapView


class MainFragment : BaseFragment<FragmentMainBinding>(com.cookandroid.happycup.R.layout.fragment_main) {

    override fun init() {
        super.init()
        val mapView = MapView(requireActivity())
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)
    }

}