package com.example.beerapp.features.beerCatalog.domain.usecases

import com.example.beerapp.features.beerCatalog.data.models.BeerModel
import com.example.beerapp.features.beerCatalog.domain.params.BeerDetailParams
import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import com.example.beerapp.util.Resource
import javax.inject.Inject

class GetBeerDetailUseCase @Inject constructor(
    private val repository: BeerRepository
){
    suspend fun call(params: BeerDetailParams): Resource<BeerModel> {
        return repository.getBeerDetail(params)
    }
}