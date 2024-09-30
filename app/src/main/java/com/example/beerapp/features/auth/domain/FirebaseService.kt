package com.example.beerapp.features.auth.domain

import com.example.beerapp.features.auth.data.model.User
import com.example.beerapp.features.auth.data.model.UserData
import kotlinx.coroutines.flow.Flow


interface FirebaseService {
    val user: Flow<User>
    val userData: Flow<UserData>
}