package com.example.beerapp.features.beerCatalog.presentation.beerList.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel.BeerListEvent
import com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel.BeerListState
import com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel.BeerListViewModel
import com.example.beerapp.ui.globalViews.bottomNavBar.BottomNavBar
import com.example.beerapp.util.LoadingIndicator
import com.example.beerapp.util.UiSingleTimeEvent


@Composable
fun BeerListScreen(
    navigator: NavHostController,
    viewModel: BeerListViewModel = hiltViewModel(),
) {
    val user = viewModel.accountService.getUserProfile()
    Scaffold(
        bottomBar = { BottomNavBar(navigator, user.isAnonymous)}
    ) {innerPadding ->
        val state = viewModel.state.collectAsState(initial = BeerListState.Empty)

        when (state.value) {
            is BeerListState.Loading -> {
                LoadingIndicator()
            }

            is BeerListState.Failure -> {
                val errorText = (state.value as BeerListState.Failure).errorText
                Text("Error: $errorText")
            }

            is BeerListState.Success -> {

                LaunchedEffect(key1 = true) {
                    viewModel.uiSingleTimeEvent.collect { event ->
                        when (event) {
                            is UiSingleTimeEvent.Navigate -> navigator.navigate(event.route)
                            else -> Unit
                        }
                    }
                }

                val beerList =
                    (state.value as BeerListState.Success).beerList.collectAsState(emptyList())
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(beerList.value) { beer ->
                        BeerItem(beer = beer, modifier = Modifier.clickable {
                            viewModel.onEvent(BeerListEvent.OnBeerClick(beer.id))
                        }, onHartClick = { viewModel.onEvent(BeerListEvent.OnHartClick(it)) })
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            else -> Unit
        }
    }
}