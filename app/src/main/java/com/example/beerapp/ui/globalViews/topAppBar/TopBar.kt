package com.example.beerapp.ui.globalViews.topAppBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.beerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onLogoutClicked: () -> Unit){
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = onLogoutClicked ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, "Log out")
            }
        })
}