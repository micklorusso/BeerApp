package com.example.beerapp.features.auth.presentation.account_center

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.beerapp.R
import com.example.beerapp.features.auth.data.model.User
import com.example.beerapp.features.auth.data.model.UserData
import com.example.beerapp.ui.globalViews.bottomNavBar.BottomNavBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun AccountCenterScreen(
    navigator: NavHostController,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountCenterViewModel
) {
    val userData by viewModel.firebaseService.userData.collectAsState(UserData())
    val user by viewModel.firebaseService.user.collectAsState(User())

    Scaffold(bottomBar = { BottomNavBar(navigator, user.isAnonymous)}) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = { Text(stringResource(R.string.account_center)) })

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            DisplayNameCard(userData.displayName) { viewModel.onUpdateDisplayNameClick(it) }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Card(modifier = Modifier.card()) {
                Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                    if (!user.isAnonymous) {
                        Text(
                            text = String.format(stringResource(R.string.profile_email), user.email),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        )
                    }

                    // ⚠️This is for demonstration purposes only, it's not a common
                    // practice to show the unique ID or account provider of an account⚠️
                    Text(
                        text = String.format(stringResource(R.string.profile_uid), user.id),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )

                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            if (user.isAnonymous) {
                AccountCenterCard(stringResource(R.string.sign_in), Icons.Filled.Face, Modifier.card()) {
                    viewModel.onSignInClick(restartApp)
                }

                AccountCenterCard(stringResource(R.string.sign_up), Icons.Filled.AccountCircle, Modifier.card()) {
                    viewModel.onSignUpClick(restartApp)
                }
            } else {
                ExitAppCard { viewModel.onSignOutClick(restartApp) }
                RemoveAccountCard { viewModel.onDeleteAccountClick(restartApp) }
            }
        }
    }
}
