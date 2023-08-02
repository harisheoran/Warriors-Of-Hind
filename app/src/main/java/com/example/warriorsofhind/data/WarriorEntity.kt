package com.example.warriorsofhind.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kings")
data class WarriorEntity(
    @PrimaryKey(autoGenerate = true)
    val Id: Int = 0,
    val name: String,
    val img: String
)