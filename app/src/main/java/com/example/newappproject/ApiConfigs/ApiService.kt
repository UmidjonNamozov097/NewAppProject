package com.example.newappproject.ApiConfigs

import com.example.newappproject.RemoteDatabase.RemoteDataModel.RemoteData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("photos/random")
    fun getQueryPhotos(
        @Query("client_id") clientID: String
    ): Call<RemoteData>

}