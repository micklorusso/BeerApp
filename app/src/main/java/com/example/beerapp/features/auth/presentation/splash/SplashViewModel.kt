package com.example.beerapp.features.auth.presentation.splash

import androidx.lifecycle.ViewModel
import com.example.beerapp.navigation.Routes.BEER_LIST_SCREEN
import com.example.beerapp.navigation.Routes.SIGN_IN_SCREEN
import com.example.beerapp.navigation.Routes.SPLASH_SCREEN
import com.example.beerapp.features.auth.domain.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(BEER_LIST_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(SIGN_IN_SCREEN, SPLASH_SCREEN)
    }
}