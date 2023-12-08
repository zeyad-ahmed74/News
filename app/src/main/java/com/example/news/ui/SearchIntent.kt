package com.example.news.ui

sealed class SearchIntent {
    data class SearchForNews(val word:String):SearchIntent()
}