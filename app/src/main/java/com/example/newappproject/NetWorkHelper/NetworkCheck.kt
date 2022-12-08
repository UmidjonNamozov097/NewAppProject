package com.example.newappproject.NetWorkHelper

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.test.core.app.ApplicationProvider


class NetworkCheck {


    fun isConnected(): Boolean {
        var connected = false
        try {
            val cm = ApplicationProvider.getApplicationContext<Context>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message.toString())
        }
        return connected
    }
}