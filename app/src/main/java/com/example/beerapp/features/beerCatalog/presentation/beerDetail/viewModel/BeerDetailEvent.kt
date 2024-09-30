package com.example.beerapp.features.beerCatalog.presentation.beerDetail.viewModel

sealed class BeerDetailEvent {
    object OnBackClicked : BeerDetailEvent()
}