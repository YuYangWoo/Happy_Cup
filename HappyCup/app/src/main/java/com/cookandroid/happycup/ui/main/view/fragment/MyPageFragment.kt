package com.cookandroid.happycup.ui.main.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookandroid.happycup.ui.base.BaseFragment
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.databinding.FragmentMypageBinding
import com.cookandroid.happycup.ui.adapter.MyPageAdapter
import com.cookandroid.happycup.ui.main.view.activity.LoginActivity
import org.koin.android.ext.android.bind

class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    override fun init() {
        super.init()
        logout()
        btnSetOnClick()
        recyclerList()
    }

    private fun recyclerList() {
        with(binding.recyclerList) {
            adapter = MyPageAdapter()
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun btnSetOnClick() {
        binding.btnAlram.setOnClickListener {
        }

        binding.btnLeftArrow.setOnClickListener {
            findNavController().navigate(MyPageFragmentDirections.actionMyPageFragmentToMainFragment())
        }

        binding.btnSetting.setOnClickListener {
            findNavController().navigate(MyPageFragmentDirections.actionMyPageFragmentToOptionFragment())
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            MySharedPreferences.clearUser(requireContext())
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

}