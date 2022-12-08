package com.example.newappproject.LocalDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newappproject.LocalDatabase.Dao.PremiumDao
import com.example.newappproject.LocalDatabase.Entity.PremiumData

@Database(entities = [PremiumData::class], version = 1)
abstract  class AppDatabse() : RoomDatabase(){
    abstract fun getUserDao():PremiumDao


    companion object {
        private var INSTANCE: AppDatabse? = null
        @Synchronized
        fun getInstance(context: Context): AppDatabse? {
            if (INSTANCE == null) {
                synchronized(AppDatabse::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabse::class.java, "Premium").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }


    }

}