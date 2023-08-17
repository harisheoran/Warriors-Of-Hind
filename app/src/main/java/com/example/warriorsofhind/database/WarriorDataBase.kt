package com.example.warriorsofhind.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteEntity::class, WarriorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WarriorDataBase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao
    abstract fun warriorDao(): WarriorDao

    companion object {
        @Volatile
        private var Instance: WarriorDataBase? = null
        fun getDatabase(context: Context): WarriorDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WarriorDataBase::class.java, "warrior_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}