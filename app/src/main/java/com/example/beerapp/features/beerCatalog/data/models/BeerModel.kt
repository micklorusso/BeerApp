package com.example.beerapp.features.beerCatalog.data.models


import com.google.gson.annotations.SerializedName


data class BeerModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("rating")
    val rating: Rating
)