package com.example.news.data.source.remote

import com.example.news.data.model.NewResponse
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/everything?q=tesla&from=2023-11-02&sortBy=publishedAt&apiKey=33dc78dd2b3b43efbb6a104082d1d91b

const val Api_Key = "33dc78dd2b3b43efbb6a104082d1d91b"

interface WebService {

    @GET("top-headlines?apiKey=$Api_Key")
    suspend fun getNews(@Query("country") country : String):NewResponse

    @GET("/v2/everything?apiKey=$Api_Key")
    suspend fun getSpecificNews(@Query("q") wordForSearch:String):NewResponse

}