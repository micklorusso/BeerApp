package com.example.beerapp.features.beerCatalog.domain.repositories

import com.example.beerapp.features.beerCatalog.data.datasources.api.BeerApi
import com.example.beerapp.features.beerCatalog.data.models.BeerModel
import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import com.example.beerapp.features.beerCatalog.domain.params.BeerDetailParams
import com.example.beerapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface BeerRepository{

    suspend fun getBeerListFromApi(): Resource<List<BeerEntity>>


    suspend fun getBeerDetail(params: BeerDetailParams): Resource<BeerModel>

    fun getBeerListFromDatabase(): Flow<List<BeerEntity>>

    suspend fun saveBeersToDatabase(beers: List<BeerEntity>)

    suspend fun clearDatabase()

    fun getBeerFavourite(): Flow<List<BeerEntity>>

    suspend fun updateBeer(beer: BeerEntity)
}