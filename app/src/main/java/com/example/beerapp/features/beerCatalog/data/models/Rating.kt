package com.example.beerapp.features.beerCatalog.data.models


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("average")
    val average: Double,
    @SerializedName("reviews")
    val reviews: Int
)