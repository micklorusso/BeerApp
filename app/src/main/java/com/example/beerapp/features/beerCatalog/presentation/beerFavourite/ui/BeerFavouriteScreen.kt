package com.example.beerapp.features.beerCatalog.presentation.beerFavourite.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.beerapp.features.auth.data.model.User
import com.example.beerapp.features.beerCatalog.presentation.beerFavourite.viewModel.BeerFavouriteEvent
import com.example.beerapp.features.beerCatalog.presentation.beerFavourite.viewModel.BeerFavouriteViewModel
import com.example.beerapp.ui.globalViews.bottomNavBar.BottomNavBar
import com.example.beerapp.util.UiSingleTimeEvent


@Composable
fun BeerFavouriteScreen(
    navigator: NavHostController,
    viewModel: BeerFavouriteViewModel,
) {
    val user = viewModel.firebaseService.user.collectAsState(User())
    Scaffold(bottomBar = { BottomNavBar(navigator, user.value.isAnonymous)}) { innerPadding ->
        val favourites = viewModel.favourites.collectAsState(initial = emptyList())

        LaunchedEffect(key1 = true) {
            viewModel.uiSingleTimeEvent.collect { event ->
                when (event) {
                    is UiSingleTimeEvent.Navigate -> navigator.navigate(event.route)
                    else -> Unit
                }
            }
        }

        LazyColumn(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(favourites.value) { favouriteBeer ->
                BeerFavouriteItem(beer = favouriteBeer, modifier = Modifier.clickable {
                    viewModel.onEvent(BeerFavouriteEvent.OnBeerClick(favouriteBeer.id))
                }, onDeleteClick = { viewModel.onEvent(BeerFavouriteEvent.OnDeleteClick(it)) })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

