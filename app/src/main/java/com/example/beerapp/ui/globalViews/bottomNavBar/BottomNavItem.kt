package com.example.beerapp.ui.globalViews.bottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.beerapp.navigation.Routes

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector, val showWhenAnonymous: Boolean) {
    data object BeerListScreenItem : BottomNavItem(
        Routes.BEER_LIST_SCREEN, "Beer List",
        Icons.AutoMirrored.Filled.List,
        true
    )
    data object BeerFavouriteScreenItem : BottomNavItem(Routes.BEER_FAVOURITE_SCREEN, "Favourites", Icons.Filled.Favorite, false)
    data object AccountCenterScreenItem : BottomNavItem(Routes.ACCOUNT_CENTER_SCREEN, "Account Center",
        Icons.AutoMirrored.Filled.ExitToApp,
        true
    )

    companion object{
        val items = mutableListOf(
            BeerListScreenItem,
            BeerFavouriteScreenItem,
            AccountCenterScreenItem
        )
    }
}