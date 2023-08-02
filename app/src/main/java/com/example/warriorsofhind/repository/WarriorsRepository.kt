package com.example.warriorsofhind.repository

import android.service.autofill.Transformation
import androidx.compose.ui.input.key.Key.Companion.W
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.room.Database
import com.example.warriorsofhind.data.WarriorDao
import com.example.warriorsofhind.data.WarriorDatabase
import com.example.warriorsofhind.data.WarriorEntity
import com.example.warriorsofhind.domain.mappers.asDataBaseModel
import com.example.warriorsofhind.domain.mappers.asDomainModel
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.network.WarriorService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WarriorsRepository @Inject constructor(
    private val warriorService: WarriorService,
    private val warriorDatabase: WarriorDatabase
) {

    private val _warriorsList = MutableLiveData<NetworkStatusWrapper<List<King>>>()
    val warriorsList: LiveData<NetworkStatusWrapper<List<King>>>
        get() = warriorDatabase.getWarriorDao().queryWarrior().map {
            it.asDomainModel()
        }


    private val _data = MutableLiveData<NetworkStatusWrapper<List<WarriorsItem>>>()
    val data: LiveData<NetworkStatusWrapper<List<WarriorsItem>>>
        get() = _data

    suspend fun getWarriorsData(query: String) {
        try {
            _data.postValue(NetworkStatusWrapper.Loading())
            val request = warriorService.getWarriors("data[?(@.name==\"${query}\")]")

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
    }

    suspend fun getKingsList(query: String) {
        try {
            // Loading Status
            _warriorsList.postValue(NetworkStatusWrapper.Loading())

            val request = warriorService.getKings(query)

            // if request was successful and response is not null, then save it it DB
            if (request.isSuccessful && request.body() != null) {
                withContext(Dispatchers.IO) {
                    val result = request.body()!!
                    val res = NetworkStatusWrapper.Success(request.body()!!)
                    warriorDatabase.getWarriorDao().insertWarrior(result.asDataBaseModel())
                }
            } else if (request.errorBody() != null) {
                _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
            } else {
                _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
            }
        } catch (e: Exception) {
            _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
        }
    }
}