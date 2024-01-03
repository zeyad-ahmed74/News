package com.example.news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.NewResponse
import com.example.news.databinding.ItemPostPagerBinding
import com.example.news.ui.util.TimeAndDate

class ViewPagerAdapter(
    private val new:NewResponse?=null,
    private val listener:RecyclerClickListener?=null
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
          binding.apply{
              newsHeader.text = article?.title
              newsDate.text = TimeAndDate.dateFormat(article?.publishedAt)
          }

        }


    }

    inner class ViewPagerHolder(val binding : ItemPostPagerBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.onItemClick(new?.articles?.get(adapterPosition))
            }
            binding.bookMark.setOnClickListener {
                listener?.onBookMarkClicked(new?.articles?.get(adapterPosition))
            }
        }
    }


    interface RecyclerClickListener{
        fun onItemClick(articlesItem: ArticlesItem?)
        fun onBookMarkClicked(articlesItem: ArticlesItem?)
    }



}