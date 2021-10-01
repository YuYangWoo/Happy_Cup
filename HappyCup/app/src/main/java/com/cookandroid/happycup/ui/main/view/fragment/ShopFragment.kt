package com.cookandroid.happycup.ui.main.view.fragment

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.FragmentShopBinding
import com.cookandroid.happycup.ui.base.BaseFragment

class ShopFragment : BaseFragment<FragmentShopBinding>(R.layout.fragment_shop) {

    override fun init() {
        super.init()
    binding.bnv.setupWithNavController(findNavController())
    }

}