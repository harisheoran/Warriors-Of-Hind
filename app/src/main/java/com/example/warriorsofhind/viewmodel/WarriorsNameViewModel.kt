package com.example.warriorsofhind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.network.ApiResponse
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.repository.WarriorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WarriorsNameViewModel @Inject constructor(private val repository: WarriorsRepository) :
    ViewModel() {

    // State Flow for Home Screen Warrior response from Repository
    val warriorsStateFlow: StateFlow<ApiResponse<List<King>>>
        get() = repository.warriorsStateFlow

    val warriorsNameListState: LiveData<NetworkStatusWrapper<List<King>>>
        get() = repository.warriorsList

    val favourites: LiveData<List<King>>
        get() = repository.favourites

    init {
        loadWarriorsNames("$.kings[*]")
    }

    // loading Warriors Home Screen Data by passing the query
    fun loadWarriorsNames(query: String) {
        viewModelScope.launch {
            repository.getWarriors(query)
        }
    }

    fun saveFavouriteKing(favouriteKing: King) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.saveFavourite(favouriteKing)
            }
        }
    }
}