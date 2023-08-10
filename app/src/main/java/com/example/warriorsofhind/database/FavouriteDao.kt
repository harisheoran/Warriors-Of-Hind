package com.example.warriorsofhind.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWarrior(favouriteEntity: FavouriteEntity)

    @Query("SELECT * from favourites")
    fun queryWarriors(): LiveData<List<FavouriteEntity>>

}