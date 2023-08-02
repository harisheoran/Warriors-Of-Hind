package com.example.warriorsofhind.domain.mappers

import com.example.warriorsofhind.data.WarriorEntity
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.network.NetworkStatusWrapper

// Data Transfer Object -> Database Objects
fun List<King>.asDataBaseModel(): List<WarriorEntity> {
    return map {
        WarriorEntity(
            name = it.name,
            img = it.img
        )
    }
}

// Database Objects -> Domain Objects
fun List<WarriorEntity>.asDomainModel(): NetworkStatusWrapper.Success<List<King>> {
    return NetworkStatusWrapper.Success(
        data = map {
            King(
                name = it.name,
                img = it.img
            )
        }
    )
}

