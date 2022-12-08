package com.example.newappproject.ApiConfigs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private  var retrofit :Retrofit? =  null

    fun getClient():Retrofit{
        if(retrofit==null){
            retrofit  = Retrofit.Builder().baseUrl("https://api.unsplash.com/").addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}