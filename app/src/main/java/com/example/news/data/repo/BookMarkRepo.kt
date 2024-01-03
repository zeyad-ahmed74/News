package com.example.news.data.repo

import androidx.lifecycle.LiveData
import com.example.news.data.model.ArticlesItem
import com.example.news.data.source.local.BookMarkDao

class BookMarkRepo(
   private val bookMarkDao: BookMarkDao? = null
){

    suspend fun addBookMark(articlesItem: ArticlesItem) = bookMarkDao?.insertBookMark(articlesItem)

    fun getBookMarks():LiveData<List<ArticlesItem>?>? = bookMarkDao?.getBookMarks()

    suspend fun deleteBookMark(articlesItem: ArticlesItem) = bookMarkDao?.deleteBookMark(articlesItem)

}