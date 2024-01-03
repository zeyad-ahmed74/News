package com.example.news.ui.searchscreen

sealed class SearchIntent {
    data class SearchForNews(val word:String): SearchIntent()
}