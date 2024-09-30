package com.example.beerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.beerapp.features.auth.presentation.account_center.AccountCenterScreen
import com.example.beerapp.features.auth.presentation.account_center.AccountCenterViewModel
import com.example.beerapp.features.auth.presentation.sign_in.SignInScreen
import com.example.beerapp.features.auth.presentation.sign_in.SignInViewModel
import com.example.beerapp.features.auth.presentation.sign_up.SignUpScreen
import com.example.beerapp.features.auth.presentation.sign_up.SignUpViewModel
import com.example.beerapp.features.auth.presentation.splash.SplashScreen
import com.example.beerapp.features.beerCatalog.presentation.beerDetail.ui.BeerDetailScreen
import com.example.beerapp.features.beerCatalog.presentation.beerFavourite.ui.BeerFavouriteScreen
import com.example.beerapp.features.beerCatalog.presentation.beerFavourite.viewModel.BeerFavouriteViewModel
import com.example.beerapp.features.beerCatalog.presentation.beerList.ui.BeerListScreen
import com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel.BeerListViewModel
import com.example.beerapp.navigation.Routes.ACCOUNT_CENTER_SCREEN
import com.example.beerapp.navigation.Routes.SIGN_IN_SCREEN
import com.example.beerapp.navigation.Routes.SIGN_UP_SCREEN
import com.example.beerapp.navigation.Routes.SPLASH_SCREEN


@Composable
fun SetupNavGraph(navController: NavHostController, navState: NavState){
    val accountCenterViewModel: AccountCenterViewModel = hiltViewModel<AccountCenterViewModel>()
    val beerFavouriteViewModel: BeerFavouriteViewModel = hiltViewModel<BeerFavouriteViewModel>()

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN,
        modifier = Modifier
    ){
        composable(Routes.BEER_LIST_SCREEN){
            BeerListScreen(navController)
        }

        composable(Routes.BEER_FAVOURITE_SCREEN) {
            BeerFavouriteScreen(navController, beerFavouriteViewModel)
        }

        composable(
            route = Routes.BEER_DETAIL_SCREEN + "/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) {
                backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            BeerDetailScreen(id!!, navController)
        }

        composable(SIGN_IN_SCREEN) {
            SignInScreen(openAndPopUp = { route, popUp -> navState.navigateAndPopUp(route, popUp) })
        }

        composable(SIGN_UP_SCREEN) {
            SignUpScreen(openAndPopUp = { route, popUp -> navState.navigateAndPopUp(route, popUp) }, navState= navState)
        }

        composable(SPLASH_SCREEN) {
            SplashScreen(openAndPopUp = { route, popUp -> navState.navigateAndPopUp(route, popUp) })
        }

        composable(ACCOUNT_CENTER_SCREEN) {
            AccountCenterScreen(navController, viewModel = accountCenterViewModel, restartApp = { route -> navState.clearAndNavigate(route) })
        }

    }
}