package com.example.beerapp.features.beerCatalog.data.datasources.api.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity

@Database(
    entities = [BeerEntity::class],
    version = 2
)
abstract class BeerDatabase: RoomDatabase() {
    abstract val dao: BeerDao
}