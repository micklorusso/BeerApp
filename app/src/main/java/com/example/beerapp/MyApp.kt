package com.example.beerapp

import android.app.Application
import com.example.beerapp.features.auth.domain.FirebaseService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp: Application() {}