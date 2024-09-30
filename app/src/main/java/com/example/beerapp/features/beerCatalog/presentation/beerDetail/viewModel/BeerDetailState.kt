package com.example.beerapp.features.beerCatalog.presentation.beerDetail.viewModel

import com.example.beerapp.features.beerCatalog.data.models.BeerModel

sealed class BeerDetailState {

    class Success(val beerDetail: BeerModel): BeerDetailState()
    class Failure(val errorText: String): BeerDetailState()
    data object Loading : BeerDetailState()
    data object Empty : BeerDetailState()

}