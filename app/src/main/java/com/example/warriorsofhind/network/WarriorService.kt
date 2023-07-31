package com.example.warriorsofhind.network

import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.models.WarriorsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface WarriorService {

    @GET("/v3/b/64c4d2feb89b1e2299c7bf95?meta=false")
    suspend fun getWarriors(@Header("X-JSON-Path") name: String): Response<List<WarriorsItem>>

    @GET("/v3/b/64c4fe07b89b1e2299c7cef8?meta=false")
    suspend fun getKings(@Header("X-JSON-Path") kings: String): Response<List<King>>

}