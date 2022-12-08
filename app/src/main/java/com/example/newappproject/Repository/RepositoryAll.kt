package com.example.newappproject.Repository

import com.example.newappproject.ApiConfigs.ApiService
import com.example.newappproject.LocalDatabase.AppDatabse
import com.example.newappproject.LocalDatabase.Entity.PremiumData

class RepositoryAll(var apiService: ApiService,var appDatabse: AppDatabse) {

    suspend fun getRemoteData() = apiService.getQueryPhotos("mT8hj53DywChJkbscZAN5aHio9v2M9impW_i-VIc7vs")
    suspend fun getLocalData() =  appDatabse.getUserDao()

    fun writeData(premiumData: PremiumData)  = appDatabse.getUserDao().InsertData(premiumData)
    fun updateData(premiumData: PremiumData)=appDatabse.getUserDao().UpdateData(premiumData)



}