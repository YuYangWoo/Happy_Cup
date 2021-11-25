package com.cookandroid.happycup.ui.main.view.fragment

import android.graphics.Color
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.FragmentPointBinding
import com.cookandroid.happycup.ui.base.BaseFragment
import android.text.Spanned

import android.text.style.ForegroundColorSpan

import android.text.Spannable
import android.text.SpannableStringBuilder


class PointFragment : BaseFragment<FragmentPointBinding>(R.layout.fragment_point) {

    override fun init() {
        super.init()

        val builder = SpannableStringBuilder(binding.txtPoint.text.toString()).apply {
            setSpan(ForegroundColorSpan(resources.getColor(R.color.mainColor,null)),0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        val txtTopicBuilder = SpannableStringBuilder(binding.txtTopic.text.toString()).apply {
            setSpan(ForegroundColorSpan(resources.getColor(R.color.mainColor,null)),19,25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.txtPoint.text = builder
        binding.txtTopic.text = txtTopicBuilder

        Glide.with(requireContext()).load(R.raw.tree).into(binding.imgTree)
        binding.btnCupping.setOnClickListener {
            findNavController().navigate(PointFragmentDirections.actionPointFragmentToShopFragment())
        }
        binding.btnMap.setOnClickListener {
            findNavController().navigate(PointFragmentDirections.actionPointFragmentToMainFragment())
        }
    }
}