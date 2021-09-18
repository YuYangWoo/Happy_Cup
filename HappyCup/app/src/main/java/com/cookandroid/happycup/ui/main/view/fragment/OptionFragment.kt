package com.cookandroid.happycup.ui.main.view.fragment

import android.content.Intent
import com.cookandroid.happycup.ui.base.BaseFragment
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.databinding.FragmentOptionBinding
import com.cookandroid.happycup.ui.main.view.activity.LoginActivity

class OptionFragment : BaseFragment<FragmentOptionBinding>(R.layout.fragment_option) {

    override fun init() {
        super.init()
        logout()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            MySharedPreferences.clearUser(requireContext())
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

}
