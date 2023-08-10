package com.example.warriorsofhind.domain

import com.example.warriorsofhind.database.FavouriteEntity
import com.example.warriorsofhind.models.King

fun King.asDataBaseModel(): FavouriteEntity {
    return FavouriteEntity(
        name = this.name,
        img = this.img,
        isFavourite = this.isFavourite
    )
}

fun List<FavouriteEntity>.asDomainModel(): List<King> {
    return map {
        King(
            name = it.name,
            img = it.img,
            isFavourite = it.isFavourite
        )
    }
}