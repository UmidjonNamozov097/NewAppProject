package com.example.newappproject.LocalDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Premium")
data class PremiumData(

    @PrimaryKey(autoGenerate = true)
    var id:Int? =0,
    var grammer:Boolean?=false,
    var vocablary:Boolean? = false,
    var spiking:Boolean? = false,
    var listining:Boolean? = false,
    var homework:Boolean? = false)
