package com.example.beerapp.di

import com.example.beerapp.features.beerCatalog.data.datasources.api.BeerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



private const val BASE_URL = "https://api.sampleapis.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBeerApi(retrofit: Retrofit): BeerApi {
        return retrofit.create(BeerApi::class.java)
    }


}