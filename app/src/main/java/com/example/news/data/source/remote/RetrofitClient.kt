package com.example.news.data.source.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL = "https://newsapi.org/v2/"
object RetrofitClient{
    private var retrofit :Retrofit? = null


    fun getWebService() :WebService?{
       if(retrofit == null){
          retrofit = Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build()
       }
        return retrofit?.create(WebService::class.java)
    }
}