package com.cookandroid.happycup.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.happycup.R

class ViewPagerAdapter2(idolList: ArrayList<Int>) : RecyclerView.Adapter<ViewPagerAdapter2.PagerViewHolder>() {
    var item = idolList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.idol.setImageResource(item[position])
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.ad_list, parent, false)){

        val idol = itemView.findViewById<ImageView>(R.id.imageView_idol1)!!
    }
}