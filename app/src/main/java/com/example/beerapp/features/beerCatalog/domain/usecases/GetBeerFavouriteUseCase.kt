package com.example.beerapp.features.beerCatalog.domain.usecases

import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeerFavouriteUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    fun call(): Flow<List<BeerEntity>> {
        return repository.getBeerFavourite()
    }
}