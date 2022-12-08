package com.example.newappproject.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newappproject.NetWorkHelper.NetworkCheck
import com.example.newappproject.Repository.RepositoryAll

class ViewModelFactory(private var networkCheck:NetworkCheck,private var repositoryAll: RepositoryAll):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyDataViewModel::class.java)){
            return MyDataViewModel(networkCheck,repositoryAll) as T
        }
        throw IllegalAccessException("Error")
    }
}