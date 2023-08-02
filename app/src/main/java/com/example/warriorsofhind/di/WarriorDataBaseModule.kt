package com.example.warriorsofhind.di

import android.content.Context
import com.example.warriorsofhind.data.WarriorDao
import com.example.warriorsofhind.data.WarriorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WarriorDataBaseModule {

    @Singleton
    @Provides
    fun provideDataBaseModule(@ApplicationContext context: Context): WarriorDatabase {
        return WarriorDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideWarriorDao(database: WarriorDatabase): WarriorDao {
        return database.getWarriorDao()
    }
}