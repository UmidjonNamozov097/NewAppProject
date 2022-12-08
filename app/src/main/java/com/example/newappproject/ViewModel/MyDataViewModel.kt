package com.example.newappproject.ViewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.newappproject.LocalDatabase.Entity.PremiumData
import com.example.newappproject.NetWorkHelper.NetworkCheck
import com.example.newappproject.RemoteDatabase.RemoteDataModel.RemoteData
import com.example.newappproject.Repository.RepositoryAll
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyDataViewModel
    (    private var networkCheck:NetworkCheck,private var repositoryAll: RepositoryAll
): ViewModel(){
    private var remoteData =  MutableLiveData<RemoteData>()
    private var localData =  MutableLiveData<PremiumData>()


    init {
        fetchData()
    }

    private  fun  fetchData(){
        viewModelScope.launch {
           var remoteDatas  = repositoryAll.getRemoteData()
            remoteDatas.enqueue(object :Callback<RemoteData>{
                override fun onResponse(call: Call<RemoteData>, response: Response<RemoteData>) {
                    remoteData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteData>, t: Throwable) {
                    remoteData.postValue(null)
                }
            })

            var localDatas  = repositoryAll.getLocalData()
            if(localDatas.getPremiumData().isNotEmpty()){
                localData.postValue(localDatas.getPremiumData()[0])
            }

        }

    }
    fun getRemotedaFun():MutableLiveData<RemoteData> = remoteData

     fun insertDataFirstTime(premiumData: PremiumData){
        repositoryAll.writeData(premiumData)

    }
    fun getLocalData():MutableLiveData<PremiumData>  = localData

    fun updateDataDatabase(premiumData: PremiumData){
        repositoryAll.updateData(premiumData)
    }


}