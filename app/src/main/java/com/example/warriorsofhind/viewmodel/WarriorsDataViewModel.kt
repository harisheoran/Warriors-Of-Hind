package com.example.warriorsofhind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.repository.WarriorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarriorsDataViewModel @Inject constructor(
    private val repository: WarriorsRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    val warriorsDataListState: LiveData<NetworkStatusWrapper<List<WarriorsItem>>>
        get() = repository.data

    init {
        val kingName = savedStateHandle.get<String>("kingName") ?: "suraj"
        loadWarriorsData(kingName)
    }

    fun loadWarriorsData(query: String) {
        viewModelScope.launch {
            repository.getWarriorsData(query)
        }
    }

}