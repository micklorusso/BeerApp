package com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel

import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity

sealed class BeerListEvent {
    data class OnBeerClick(val beerId: Int): BeerListEvent()
    data class OnHartClick(val beer: BeerEntity): BeerListEvent()
}