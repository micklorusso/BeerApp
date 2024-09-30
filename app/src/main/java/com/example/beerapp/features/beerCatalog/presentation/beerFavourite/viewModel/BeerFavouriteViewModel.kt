package com.example.beerapp.features.beerCatalog.presentation.beerFavourite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.features.auth.AuthViewModel
import com.example.beerapp.features.auth.domain.AccountService
import com.example.beerapp.features.auth.domain.FirebaseService
import com.example.beerapp.features.auth.domain.StorageService
import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import com.example.beerapp.features.beerCatalog.domain.usecases.GetBeerFavouriteUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.UpdateBeerUseCase
import com.example.beerapp.navigation.Routes
import com.example.beerapp.util.UiSingleTimeEvent
import com.example.beerapp.util.UiSingleTimeEvent.Companion.emitUiSingleTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerFavouriteViewModel @Inject constructor(
    getBeerFavouriteUseCase: GetBeerFavouriteUseCase,
    private val updateBeerUseCase: UpdateBeerUseCase,
    private val storageService: StorageService,
    val firebaseService: FirebaseService
): ViewModel() {

    val favourites: Flow<List<BeerEntity>> = getBeerFavouriteUseCase.call()

    private val _uiSingleTimeEvent = MutableSharedFlow<UiSingleTimeEvent>()
    val uiSingleTimeEvent = _uiSingleTimeEvent.asSharedFlow()

    fun onEvent(event: BeerFavouriteEvent){
        when(event){
            is BeerFavouriteEvent.OnBeerClick -> {
                emitUiSingleTimeEvent(UiSingleTimeEvent.Navigate("${Routes.BEER_DETAIL_SCREEN}/${event.beerId}"), viewModelScope, _uiSingleTimeEvent)
            }
            is BeerFavouriteEvent.OnDeleteClick -> {
                viewModelScope.launch {
                    event.beer.isFavourite = !event.beer.isFavourite
                    updateBeerUseCase.call(event.beer)
                    val userData = firebaseService.userData.first()
                    val favourites = userData.favourites
                    favourites.remove(event.beer.id)
                    storageService.updateUserData(userData.copy(favourites=favourites))
                }
            }
        }
    }

}