package com.example.beerapp.di

import com.example.beerapp.features.beerCatalog.data.datasources.api.BeerApi
import com.example.beerapp.features.beerCatalog.data.datasources.api.localDatabase.BeerDatabase
import com.example.beerapp.features.beerCatalog.data.repositories.BeerRepositoryImpl
import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBeerRepository(api: BeerApi, db: BeerDatabase): BeerRepository{
        return BeerRepositoryImpl(api, db.dao)
    }

}