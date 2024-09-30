package com.example.beerapp.features.beerCatalog.domain.usecases

import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import com.example.beerapp.util.Resource
import javax.inject.Inject

class GetBeerListFromApiUseCase @Inject constructor(
    private val repository: BeerRepository
){

    suspend fun call(): Resource<List<BeerEntity>> {
        return repository.getBeerListFromApi()
    }

}