package com.example.beerapp.features.beerCatalog.presentation.beerFavourite.viewModel

import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity

sealed class BeerFavouriteEvent {
    data class OnBeerClick(val beerId: Int): BeerFavouriteEvent()
    data class OnDeleteClick(val beer: BeerEntity): BeerFavouriteEvent()
}