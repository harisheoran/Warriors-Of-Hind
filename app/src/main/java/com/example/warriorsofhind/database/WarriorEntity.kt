package com.example.warriorsofhind.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warriors")
data class WarriorEntity(
    @PrimaryKey
    val Id: Int,
    val name: String,
    val img: String
)