package com.example.news.ui

import com.example.news.data.model.NewResponse

sealed class SearchViewState {
    data object Loading:SearchViewState()
    data class SpecificNews(var newsResponse: NewResponse?=null):SearchViewState()
    data class Error(var error : String?=null):SearchViewState()
}