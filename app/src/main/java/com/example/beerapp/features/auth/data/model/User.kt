package com.example.beerapp.features.auth.data.model

data class User(
    val id: String = "",
    val email: String = "",
    val isAnonymous: Boolean = true
)