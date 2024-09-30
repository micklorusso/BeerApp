package com.example.beerapp.features.auth.presentation.sign_up

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.example.beerapp.features.auth.AuthViewModel
import com.example.beerapp.features.auth.AuthViewModel.Companion.ERROR_TAG
import com.example.beerapp.navigation.Routes.BEER_LIST_SCREEN
import com.example.beerapp.navigation.Routes.SIGN_UP_SCREEN
import com.example.beerapp.util.Constants.UNEXPECTED_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.example.beerapp.features.auth.domain.AccountService
import com.example.beerapp.features.auth.domain.StorageService
import com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel.BeerListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val accountService: AccountService,
): AuthViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (!_email.value.isValidEmail()) {
                throw IllegalArgumentException("Invalid email format")
            }

            if (!_password.value.isValidPassword()) {
                throw IllegalArgumentException("Invalid password format")
            }

            if (_password.value != _confirmPassword.value) {
                throw IllegalArgumentException("Passwords do not match")
            }

            accountService.signUp(_email.value, _password.value)
            openAndPopUp(BEER_LIST_SCREEN, SIGN_UP_SCREEN)
        }
    }

    fun onSignUpWithGoogle(credential: Credential, openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                accountService.linkAccountWithGoogle(googleIdTokenCredential.idToken)
                openAndPopUp(BEER_LIST_SCREEN, SIGN_UP_SCREEN)
            } else {
                Log.e(ERROR_TAG, UNEXPECTED_CREDENTIAL)
            }
        }
    }
}