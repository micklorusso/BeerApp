package com.example.beerapp.features.auth.presentation.account_center

import com.example.beerapp.features.auth.AuthViewModel
import com.example.beerapp.navigation.Routes.SIGN_IN_SCREEN
import com.example.beerapp.navigation.Routes.SIGN_UP_SCREEN
import com.example.beerapp.navigation.Routes.SPLASH_SCREEN
import com.example.beerapp.features.auth.domain.AccountService
import com.example.beerapp.features.auth.domain.FirebaseService
import com.example.beerapp.features.auth.domain.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class AccountCenterViewModel @Inject constructor(
    val accountService: AccountService,
    val storageService: StorageService,
    val firebaseService: FirebaseService
): AuthViewModel() {

    fun onUpdateDisplayNameClick(newDisplayName: String) {
        launchCatching {
            storageService.updateUserData(firebaseService.userData.first().copy(displayName = newDisplayName))
        }
    }

    fun onSignInClick(openScreen: (String) -> Unit) = openScreen(SIGN_IN_SCREEN)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    fun onDeleteAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            storageService.deleteUserData(firebaseService.userData.first().userId)
            restartApp(SPLASH_SCREEN)
        }
    }
}