package com.cookandroid.happycup.ui.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.happycup.databinding.HolderMyPageBinding
import com.cookandroid.happycup.ui.main.view.fragment.PointList

class MyPageAdapter : RecyclerView.Adapter<MyPageAdapter.ListViewHolder>() {
    var data = arrayListOf("포인트 거래내역", "커핑 새로운 기능", "회원혜택")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            HolderMyPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ListViewHolder(private val binding: HolderMyPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(d: String) {
            binding.list = d
        }

        init {
            binding.root.setOnClickListener {

                when (binding.txtOption.text.toString()) {
                    // 이벤트
                    "포인트 거래내역" -> {
                        val intent = Intent(binding.root.context, PointList::class.java)
                        val bundle = Bundle()
                        startActivity(binding.root.context,intent,bundle)
                    }
                }
            }
        }
    }
}