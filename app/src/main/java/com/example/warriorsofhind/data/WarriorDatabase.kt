package com.example.warriorsofhind.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Database(entities = [WarriorEntity::class], version = 1, exportSchema = false)
abstract class WarriorDatabase : RoomDatabase() {

    abstract fun getWarriorDao(): WarriorDao

    companion object {

        // to keep a refrence to DB, helps im maintain only one instance of DB
        /*
    The value of a volatile variable is never cached, and all reads and writes are to and from the main memory.
    These features help ensure the value of Instance is always up to date and is the same for all execution threads.
     It means that changes made by one thread to Instance are immediately visible to all other threads.
     */
        @Volatile
        private var Instance: WarriorDatabase? = null

        fun getDatabase(context: Context): WarriorDatabase {
            /*
        Multiple threads can potentially ask for a database instance at the same time, which results in two databases instead of one.
         This issue is known as a race condition. Wrapping the code to get the database inside a synchronized block means that only
         one thread of execution at a time can enter this block of code, which makes sure the database only gets initialized once.
         */

            return Instance ?: synchronized(this) {
                //  use the database builder to get the database
                Room.databaseBuilder(
                    context,
                    WarriorDatabase::class.java,
                    "warrior_database"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }      //to keep a reference to the recently created db instance.`
            }
        }
    }
}
