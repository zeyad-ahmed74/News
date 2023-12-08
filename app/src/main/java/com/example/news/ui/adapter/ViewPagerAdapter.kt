package com.example.news.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.news.R
import com.example.news.data.model.NewResponse
import com.example.news.databinding.ItemPostPagerBinding
import com.example.news.ui.MyUtils
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class ViewPagerAdapter(
    private val new:NewResponse?=null
):RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding = ItemPostPagerBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return ViewPagerHolder(binding)
    }

    override fun getItemCount(): Int {
        return new?.articles?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val article = new?.articles?.get(position)
        with(holder){
          binding.apply {
              newsHeader.text = article?.title
              newsDate.text = MyUtils.dateFormat(article?.publishedAt)
          }
        }




    }

    inner class ViewPagerHolder(val binding : ItemPostPagerBinding):RecyclerView.ViewHolder(binding.root)

}