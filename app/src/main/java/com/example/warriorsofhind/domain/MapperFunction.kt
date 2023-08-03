package com.example.warriorsofhind.domain

import com.example.warriorsofhind.database.FavouriteEntity
import com.example.warriorsofhind.models.King

fun King.asDataBaseModel(): FavouriteEntity {
    return FavouriteEntity(
        name = this.name,
        img = this.img
    )
}

fun List<FavouriteEntity>.asDomainModel(): List<King> {
    return map {
        King(
            name = it.name,
            img = it.img
        )
    }
}