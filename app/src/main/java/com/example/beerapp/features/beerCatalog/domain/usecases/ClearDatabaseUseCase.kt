package com.example.beerapp.features.beerCatalog.domain.usecases

import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import javax.inject.Inject

class ClearDatabaseUseCase @Inject constructor(
    private val repository: BeerRepository
){
    suspend fun call() {
        repository.clearDatabase()
    }
}