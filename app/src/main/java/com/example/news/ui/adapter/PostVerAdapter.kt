package com.example.news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.airbnb.lottie.utils.Utils
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.NewResponse
import com.example.news.databinding.ItemPostRecBinding
import com.example.news.ui.MyUtils
import java.text.DateFormat
import java.time.format.DateTimeFormatter

class PostVerAdapter(
    private val new:NewResponse?=null,
    val recyclerListener:RecyclerViewListener
):RecyclerView.Adapter<PostVerAdapter.PostHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ItemPostRecBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return new?.articles?.size ?: 0
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val article = new?.articles?.get(position)
        with(holder){
          binding.apply {
              newsHeader.text = article?.title

              if (article?.urlToImage == null){
                  newsImage.load(R.drawable.not_found_image){
                      crossfade(true)
                      crossfade(1000)
                      transformations(RoundedCornersTransformation(70f))
                  }
              }else{
                  newsImage.load(article.urlToImage){
                      crossfade(true)
                      crossfade(1000)
                      transformations(RoundedCornersTransformation(70f))
                  }
              }
              newsDate.text = MyUtils.dateFormat(article?.publishedAt)
          }
        }
    }

    inner class PostHolder(val binding : ItemPostRecBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                recyclerListener.onItemClicked(new?.articles?.get(adapterPosition))
            }
        }
    }

    interface RecyclerViewListener{
        fun onItemClicked(article:ArticlesItem?=null)
    }

}