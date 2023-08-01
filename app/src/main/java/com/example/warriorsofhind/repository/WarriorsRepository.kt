package com.example.warriorsofhind.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.network.WarriorService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class WarriorsRepository @Inject constructor(private val warriorService: WarriorService) {

    private val _warriorsList = MutableLiveData<NetworkStatusWrapper<List<King>>>()
    val warriorsList: LiveData<NetworkStatusWrapper<List<King>>>
        get() = _warriorsList


    private val _data = MutableLiveData<NetworkStatusWrapper<List<WarriorsItem>>>()
    val data: LiveData<NetworkStatusWrapper<List<WarriorsItem>>>
        get() = _data

    suspend fun getWarriorsData(query: String) {
        try{
            _data.postValue(NetworkStatusWrapper.Loading())
            val request = warriorService.getWarriors("data[?(@.name==\"${query}\")]")

            if (request.isSuccessful && request.body() != null) {
                _data.postValue(NetworkStatusWrapper.Success(request.body()!!))
            } else if (request.errorBody() != null) {
                _data.postValue(NetworkStatusWrapper.Failure("Error"))
            } else {
                _data.postValue(NetworkStatusWrapper.Failure())
            }
        }catch (e: Exception){
            _data.postValue(NetworkStatusWrapper.Failure())

        }
    }

    suspend fun getKingsList(query: String) {
        try {
            _warriorsList.postValue(NetworkStatusWrapper.Loading())
            val request = warriorService.getKings(query)
            if (request.isSuccessful && request.body() != null) {
                _warriorsList.postValue(NetworkStatusWrapper.Success(request.body()!!))
            } else if (request.errorBody() != null) {
                _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
            } else {
                _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
            }
        }catch (e:Exception){
            _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
        }
    }
}