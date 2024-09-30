package com.example.beerapp.ui.globalViews.appBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.beerapp.navigation.NavState
import com.example.beerapp.navigation.Routes.SIGN_IN_SCREEN
import com.example.beerapp.navigation.Routes.SIGN_UP_SCREEN


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navState: NavState){
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navState.navigateAndPopUp(SIGN_IN_SCREEN, SIGN_UP_SCREEN) }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = { Text("Sign Up") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}