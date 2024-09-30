package com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.features.auth.data.model.UserData
import com.example.beerapp.features.auth.domain.AccountService
import com.example.beerapp.features.auth.domain.FirebaseService
import com.example.beerapp.features.auth.domain.StorageService
import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import com.example.beerapp.features.beerCatalog.domain.usecases.ClearDatabaseUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.GetBeerListFromApiUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.GetBeerListFromDatabaseUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.SaveBeersToDatabaseUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.UpdateBeerUseCase
import com.example.beerapp.util.Resource
import com.example.beerapp.navigation.Routes
import com.example.beerapp.util.UiSingleTimeEvent
import com.example.beerapp.util.UiSingleTimeEvent.Companion.emitUiSingleTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val getBeerListFromApiUseCase: GetBeerListFromApiUseCase,
    private val getBeerListFromDatabaseUseCase: GetBeerListFromDatabaseUseCase,
    private val clearDatabaseUseCase: ClearDatabaseUseCase,
    private val saveBeersToDatabaseUseCase: SaveBeersToDatabaseUseCase,
    private val updateBeerUseCase: UpdateBeerUseCase,
    val accountService: AccountService,
    private val storageService: StorageService,
    private val firebaseService: FirebaseService
    ): ViewModel() {

    private val _state = MutableStateFlow<BeerListState>(BeerListState.Empty)
    val state: StateFlow<BeerListState> = _state.asStateFlow()

    private val _uiSingleTimeEvent = MutableSharedFlow<UiSingleTimeEvent>()
    val uiSingleTimeEvent = _uiSingleTimeEvent.asSharedFlow()

    private var currentUserId: String? = null

    init {
        viewModelScope.launch {
            firebaseService.user.collect { user ->
                if(user.id != currentUserId){
                    currentUserId = user.id
                    reloadBeerList()
                }
            }
        }
    }

    private suspend fun reloadBeerList(){
            _state.value = BeerListState.Loading
            when(val beerListResource = getBeerListFromApiUseCase.call()){
                is Resource.Error -> _state.value = BeerListState.Failure(beerListResource.message!!)
                is Resource.Success -> {
                    val beerList: List<BeerEntity> = beerListResource.data!!
                    if (beerList.isEmpty()) {
                        _state.value =
                            BeerListState.Failure("/beers/ale: Empty list returned by the API")
                    } else {
                        clearDatabaseUseCase.call()
                        val favourites = firebaseService.userData.first().favourites
                        for(i in beerList.indices)
                            if(favourites.contains(beerList[i].id))
                                beerList[i].isFavourite = true
                        saveBeersToDatabaseUseCase.call(beerList)
                        _state.value = BeerListState.Success(getBeerListFromDatabaseUseCase.call())
                    }
                }
        }
        Log.d(TAG, "Beer List reloaded")
    }


    fun onEvent(event: BeerListEvent){
        when(event){
            is BeerListEvent.OnBeerClick -> {
                emitUiSingleTimeEvent(UiSingleTimeEvent.Navigate("${Routes.BEER_DETAIL_SCREEN}/${event.beerId}"), viewModelScope, _uiSingleTimeEvent)
            }
            is BeerListEvent.OnHartClick -> {
                viewModelScope.launch {
                    val isFavourite = !event.beer.isFavourite
                    val favouriteChangeId = event.beer.id
                    val updatedBeer = event.beer.copy(isFavourite = isFavourite)
                    val userData: UserData = firebaseService.userData.first()

                    updateBeerUseCase.call(updatedBeer)

                    val updatedFavourites = userData.favourites.toMutableList()

                    if (isFavourite) {
                        if(!updatedFavourites.contains(favouriteChangeId))
                            updatedFavourites.add(favouriteChangeId)
                    } else {
                        updatedFavourites.remove(favouriteChangeId)
                    }

                    storageService.updateUserData(userData.copy(favourites = updatedFavourites))

                }
            }
        }
    }

    companion object{
        private const val TAG = "BeerListViewModel"
    }

}