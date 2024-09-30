package com.example.beerapp.features.beerCatalog.data.datasources.api

import com.example.beerapp.features.beerCatalog.data.models.BeerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BeerApi {

    @GET("/beers/ale")
    suspend fun getBeerList(): Response<List<BeerModel>>


    @GET("/beers/ale/{id}")
    suspend fun getBeerDetail(@Path("id") id: Int): Response<BeerModel>

}