package com.example.newappproject.LocalDatabase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.newappproject.LocalDatabase.Entity.PremiumData

@Dao
interface PremiumDao {
    @Insert
    fun InsertData(premiumData: PremiumData)

    @Query("Select *from Premium")
    fun getPremiumData():List<PremiumData>

    @Update
    fun UpdateData(premiumData: PremiumData)
}