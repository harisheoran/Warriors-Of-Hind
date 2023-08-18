package com.example.warriorsofhind.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface WarriorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWarriors(warriors: List<WarriorEntity>)

}