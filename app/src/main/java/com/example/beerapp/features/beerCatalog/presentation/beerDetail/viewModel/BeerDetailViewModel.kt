package com.example.beerapp.features.beerCatalog.presentation.beerDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.features.beerCatalog.data.models.BeerModel
import com.example.beerapp.features.beerCatalog.domain.params.BeerDetailParams
import com.example.beerapp.features.beerCatalog.domain.usecases.GetBeerDetailUseCase
import com.example.beerapp.util.Resource
import com.example.beerapp.util.UiSingleTimeEvent
import com.example.beerapp.util.UiSingleTimeEvent.Companion.emitUiSingleTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val useCase: GetBeerDetailUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<BeerDetailState>(BeerDetailState.Empty)
    val state: StateFlow<BeerDetailState> = _state.asStateFlow()

    private val _uiSingleTimeEvent = MutableSharedFlow<UiSingleTimeEvent>()
    val uiSingleTimeEvent = _uiSingleTimeEvent.asSharedFlow()

    fun getBeerDetail(id: Int) {
        viewModelScope.launch{
            _state.value = BeerDetailState.Loading
            when(val beerDetailResource = useCase.call(BeerDetailParams(id))){
                is Resource.Error -> _state.value = BeerDetailState.Failure(beerDetailResource.message!!)
                is Resource.Success -> {
                    val beerDetail: BeerModel? = beerDetailResource.data
                    if (beerDetail == null) {
                        _state.value = BeerDetailState.Failure("/beers/ale/$id: null model returned")
                    } else {
                        _state.value = BeerDetailState.Success(beerDetail)
                    }
                }
            }
        }
    }

    fun onEvent(event: BeerDetailEvent){
        when(event){
            is BeerDetailEvent.OnBackClicked -> {
                emitUiSingleTimeEvent(UiSingleTimeEvent.PopBackStack, viewModelScope, _uiSingleTimeEvent)
            }
        }
    }

}