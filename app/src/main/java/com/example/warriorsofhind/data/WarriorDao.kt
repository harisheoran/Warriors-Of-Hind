package com.example.warriorsofhind.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WarriorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWarrior(warrior: List<WarriorEntity>)

    @Query("SELECT * from kings")
    fun queryWarrior(): LiveData<List<WarriorEntity>>

}