package com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel

import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import kotlinx.coroutines.flow.Flow

sealed class BeerListState {

    class Success(val beerList: Flow<List<BeerEntity>>): BeerListState()
    class Failure(val errorText: String): BeerListState()
    data object Loading : BeerListState()
    data object Empty : BeerListState()

}