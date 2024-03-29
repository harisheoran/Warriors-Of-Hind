package com.example.warriorsofhind.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.warriorsofhind.database.WarriorDataBase
import com.example.warriorsofhind.domain.asDataBaseModel
import com.example.warriorsofhind.domain.asDomainsModel
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.ApiClient
import com.example.warriorsofhind.network.ApiResponse
import com.example.warriorsofhind.network.NetworkStatusWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class WarriorsRepository @Inject constructor(
    private val warriorDataBase: WarriorDataBase,
    private val apiClient: ApiClient
) {


    // 01. Warriors Data (FOR HOME SCREEN)
    // State Flow for Home Screen Warrior response from network
    private val _warriorsStateFlow = MutableStateFlow<ApiResponse<List<King>>>(
        ApiResponse(
            status = ApiResponse.Status.Loading,
            data = null,
            exception = null
        )
    )
    val warriorsStateFlow: StateFlow<ApiResponse<List<King>>>
        get() = _warriorsStateFlow

    suspend fun getWarriors(query: String) {
        val request = apiClient.getWarriors(query)
        if (request.isFailed || !request.isSucceed) {
            _warriorsStateFlow.emit(
                ApiResponse(
                    status = ApiResponse.Status.Failure,
                    data = null,
                    exception = Exception()
                )
            )
        } else {

        /*    withContext(Dispatchers.IO) {
                warriorDataBase.warriorDao().insertWarriors(request.asDataBaseModel())
            }*/
            _warriorsStateFlow.emit(
                request
            )
        }
    }

    // 02. Warriors Details (FOR DETAILS SCREEN)
    private val _warriorsDetailsStateFlow = MutableStateFlow<ApiResponse<List<WarriorsItem>>>(
        ApiResponse(
            status = ApiResponse.Status.Loading,
            data = null,
            exception = null
        )
    )
    val warriorDetailsStateFlow: StateFlow<ApiResponse<List<WarriorsItem>>>
        get() = _warriorsDetailsStateFlow

    suspend fun getWarriorsDetails(query: String) {
        val request = apiClient.getWarriorsDetails("data[?(@.name==\"${query}\")]")

        if (request.isFailed || !request.isSucceed) {
            _warriorsDetailsStateFlow.emit(
                ApiResponse(
                    status = ApiResponse.Status.Failure,
                    data = null,
                    exception = Exception()
                )
            )
        } else {
            _warriorsDetailsStateFlow.emit(
                request
            )
        }
    }


    private val _warriorsList = MutableLiveData<NetworkStatusWrapper<List<King>>>()
    val warriorsList: LiveData<NetworkStatusWrapper<List<King>>>
        get() = _warriorsList


    /* suspend fun getKingsList(query: String) {
         try {
             _warriorsList.postValue(NetworkStatusWrapper.Loading())
             val request = warriorService.getWarriors(query)
             if (request.isSuccessful && request.body() != null) {
                 _warriorsList.postValue(NetworkStatusWrapper.Success(request.body()!!))
             } else if (request.errorBody() != null) {
                 _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
             } else {
                 _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
             }
         } catch (e: Exception) {
             _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
         }
     }*/


    private val _data = MutableLiveData<NetworkStatusWrapper<List<WarriorsItem>>>()
    val data: LiveData<NetworkStatusWrapper<List<WarriorsItem>>>
        get() = _data

    /* suspend fun getWarriorsData(query: String) {
         try {
             _data.postValue(NetworkStatusWrapper.Loading())
             val request = warriorService.getWarriorsDetails("data[?(@.name==\"${query}\")]")

             if (request.isSuccessful && request.body() != null) {
                 _data.postValue(NetworkStatusWrapper.Success(request.body()!!))
             } else if (request.errorBody() != null) {
                 _data.postValue(NetworkStatusWrapper.Failure("Error"))
             } else {
                 _data.postValue(NetworkStatusWrapper.Failure())
             }
         } catch (e: Exception) {
             _data.postValue(NetworkStatusWrapper.Failure())

         }
     }*/


    private val _favourites = MutableLiveData<List<King>>()
    val favourites: LiveData<List<King>>
        get() = warriorDataBase.favouriteDao().queryWarriors().map {
            it.asDomainsModel()
        }

    suspend fun saveFavourite(favouriteKing: King) {
        warriorDataBase.favouriteDao().insertWarrior(favouriteKing.asDataBaseModel())
    }

}