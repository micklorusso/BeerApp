package com.example.beerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.beerapp.navigation.NavState
import com.example.beerapp.navigation.SetupNavGraph
import com.example.beerapp.util.Constants.AUTH_PORT
import com.example.beerapp.util.Constants.FIRESTORE_PORT
import com.example.beerapp.util.Constants.LOCALHOST
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestoreSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureFirebaseServices()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navState = remember(navController) {
                NavState(navController)
            }
                SetupNavGraph(navController, navState)
        }
    }
}

private fun configureFirebaseServices() {
    if (BuildConfig.DEBUG) {
        Firebase.auth.useEmulator(LOCALHOST, AUTH_PORT)
        Firebase.firestore.useEmulator(LOCALHOST, FIRESTORE_PORT)
    }
}

