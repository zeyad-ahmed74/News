package com.example.news.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.news.data.model.ArticlesItem
import com.example.news.data.repo.BookMarkRepo
import com.example.news.data.source.local.BookMarksDB
import kotlinx.coroutines.launch

class BookMarkViewModel(application: Application):AndroidViewModel(application) {
    private var bookMarkRepo:BookMarkRepo?=null

    init {
        val bookMakDao = BookMarksDB.getBookMarkDataBase(application).getBookMarkDao()
        bookMarkRepo = BookMarkRepo(bookMakDao)
    }

    fun addBookMark(articlesItem: ArticlesItem){
        viewModelScope.launch{
            bookMarkRepo?.addBookMark(articlesItem)
        }
    }

    fun getBookMarks(): LiveData<List<ArticlesItem>?>? = bookMarkRepo?.getBookMarks()

    fun deleteBookMark(articlesItem: ArticlesItem){
        viewModelScope.launch {
            bookMarkRepo?.deleteBookMark(articlesItem)
        }
    }



}