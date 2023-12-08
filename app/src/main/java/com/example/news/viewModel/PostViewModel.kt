package com.example.news.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.source.remote.RetrofitClient
import com.example.news.ui.PostIntent
import com.example.news.ui.PostViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class PostViewModel:ViewModel(){

    val intentChannel = Channel<PostIntent>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow<PostViewState>(PostViewState.Loading)
    val viewState : StateFlow<PostViewState> get() = _viewState


    init {
        processIntent()
    }
    // process

    private fun processIntent(){
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect{
                when(it){
                    is PostIntent.GetArabicNews -> getNews("eg", true)
                    is PostIntent.GetEnglishNews -> getNews("us",false)
                }
            }
        }
    }

    private fun getNews(country : String, arabicOrNot : Boolean){
        if (arabicOrNot) {
            viewModelScope.launch {
                _viewState.value =
                    try {
                        PostViewState.ArabicNews(RetrofitClient.getWebService()?.getNews(country)!!)
                    } catch (e: Exception) {
                        PostViewState.Error(e.message!!)
                    }
            }
        }else{
            viewModelScope.launch {
                _viewState.value =
                    try {
                        PostViewState.EnglishNews(RetrofitClient.getWebService()?.getNews(country)!!)
                    } catch (e: Exception) {
                        PostViewState.Error(e.message!!)
                    }
            }
        }
  }

//    private fun getEnglishNews(country : String){
//        viewModelScope.launch{
//            _viewState.value =
//                try {
//                    PostViewState.EnglishNews(RetrofitClient.getWebService()?.getNews(country)!!)
//                }catch (e:Exception){
//                    PostViewState.Error(e.message!!)
//                }
//        }
//    }

}
