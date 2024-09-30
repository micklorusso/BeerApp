package com.example.beerapp.features.beerCatalog.domain.usecases

import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import javax.inject.Inject

class SaveBeersToDatabaseUseCase @Inject constructor(
    private val repository: BeerRepository
){
    suspend fun call(params: List<BeerEntity>) {
        repository.saveBeersToDatabase(params)
    }

}