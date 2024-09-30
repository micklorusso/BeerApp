package com.example.beerapp.features.beerCatalog.domain.usecases

import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import javax.inject.Inject

class UpdateBeerUseCase @Inject constructor(
    private val repository: BeerRepository
){
    suspend fun call(params: BeerEntity) {
        repository.updateBeer(params)
    }
}