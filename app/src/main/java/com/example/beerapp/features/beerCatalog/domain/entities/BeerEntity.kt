package com.example.beerapp.features.beerCatalog.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer_table")
data class BeerEntity(
    @PrimaryKey
    val id: Int,
    val image: String,
    val name: String,
    val price: String,
    var isFavourite: Boolean
)