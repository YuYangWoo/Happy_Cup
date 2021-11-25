package com.cookandroid.happycup.ui.main.view.fragment

import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.FragmentPointBinding
import com.cookandroid.happycup.ui.base.BaseFragment

class PointFragment : BaseFragment<FragmentPointBinding>(R.layout.fragment_point) {

    override fun init() {
        super.init()

        Glide.with(requireContext()).load(R.raw.tree).into(binding.imgTree)
        binding.btnCupping.setOnClickListener {
            findNavController().navigate(PointFragmentDirections.actionPointFragmentToShopFragment())
        }
        binding.btnMap.setOnClickListener {
            findNavController().navigate(PointFragmentDirections.actionPointFragmentToMainFragment())
        }
    }
}