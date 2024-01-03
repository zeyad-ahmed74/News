package com.example.news.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.source.remote.RetrofitClient
import com.example.news.ui.searchscreen.SearchIntent
import com.example.news.ui.searchscreen.SearchViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class SearchViewModel :ViewModel(){

    val intentChannel = Channel<SearchIntent>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow<SearchViewState>(SearchViewState.Loading)
    val viewState : StateFlow<SearchViewState> get() = _viewState


    init {
        processIntent()
    }

    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect{
                when(it){
                    is SearchIntent.SearchForNews -> getSpecificNews(it.word)
                }
            }
        }
    }

    private fun getSpecificNews(word : String) {
        viewModelScope.launch {
            _viewState.value =
                try {
                    SearchViewState.SpecificNews(RetrofitClient.getWebService()?.getSpecificNews(word))
                }catch (e:Exception){
                   SearchViewState.Error(e.message)
                }
        }
    }
}