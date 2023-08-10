package com.example.warriorsofhind.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.warriorsofhind.WarriorsOfHindApplication
import com.example.warriorsofhind.network.WarriorService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetroFitModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.jsonbin.io")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): WarriorService {
        return retrofit.create(WarriorService::class.java)
    }

    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient{
        val client = OkHttpClient.Builder()
        client.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        client.addInterceptor(
            ChuckerInterceptor.Builder(WarriorsOfHindApplication.myContext)
                .collector(ChuckerCollector(WarriorsOfHindApplication.myContext))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        return client.build()
    }
}