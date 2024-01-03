package com.example.news.ui.homescreen

import com.example.news.data.model.NewResponse

sealed class PostViewState{
    data object Loading: PostViewState()
    data class ArabicNews(val newsResponse: NewResponse): PostViewState()
    data class EnglishNews(val newsResponse: NewResponse): PostViewState()
    data class Error(val error : String): PostViewState()
}
