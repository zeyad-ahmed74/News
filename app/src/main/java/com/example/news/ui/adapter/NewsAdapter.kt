package com.example.news.ui.adapter

import android.graphics.Color
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.databinding.ItemPostRecBinding
import com.example.news.ui.util.TimeAndDate

class NewsAdapter(
    var hideSpecificView : Boolean = false
):RecyclerView.Adapter<NewsAdapter.PostHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ItemPostRecBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val article = differ.currentList[position]
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
              newsDate.text = TimeAndDate.dateFormat(article?.publishedAt)

              itemView.setOnClickListener {
                  onItemClicked?.let { it(article)}
              }
              bookMark.setOnClickListener {
                  onBookMarkClicked?.let { it(article) }
              }

              // to hide bookMark view in BookMarkFragment
              if(hideSpecificView)
                  binding.bookMark.visibility = View.INVISIBLE
          }


        }

    }

    inner class PostHolder(val binding : ItemPostRecBinding):RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<ArticlesItem>(){
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean{
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)




    private var onItemClicked : ((ArticlesItem) -> Unit)?=null

    private var onBookMarkClicked : ((ArticlesItem) -> Unit)?=null

    fun setOnItemClickListener(listener: (ArticlesItem) -> Unit){

        onItemClicked = listener
    }

    fun setOnBookMarkClicked(listener: (ArticlesItem) -> Unit){
        onBookMarkClicked = listener
    }

}