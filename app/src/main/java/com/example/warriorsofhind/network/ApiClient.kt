package com.example.warriorsofhind.network

import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.models.WarriorsItem
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(private val warriorService: WarriorService) {

    // get warriors
    suspend fun getWarriors(query: String): ApiResponse<List<King>> {
        return safeApiCall {
            warriorService.getWarriors(query)
        }
    }

    // get warriors details
    suspend fun getWarriorsDetails(query: String): ApiResponse<List<WarriorsItem>> {
        return safeApiCall {
            warriorService.getWarriorsDetails(query)
        }
    }

    // inline function to call service and fet response and create an instance of ApiResponse using its companion object
    private inline fun <T> safeApiCall(apiCallToGetResponse: () -> Response<T>): ApiResponse<T> {
        return try {
            ApiResponse.successResponse(apiCallToGetResponse.invoke())
        } catch (e: Exception) {
            ApiResponse.failureResponse(e)
        }
    }


}