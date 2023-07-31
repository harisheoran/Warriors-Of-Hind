package com.example.warriorsofhind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.repository.WarriorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarriorsNameViewModel @Inject constructor(private val repository: WarriorsRepository) :
    ViewModel() {

    val warriorsNameListState: LiveData<NetworkStatusWrapper<List<King>>>
        get() = repository.warriorsList

    init {
        loadWarriorsNames("$.kings[*]")
    }

    fun loadWarriorsNames(query: String) {
        viewModelScope.launch {
            repository.getKingsList(query)
        }
    }
}