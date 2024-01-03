package com.example.news.data.source.remote

import com.example.news.ui.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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