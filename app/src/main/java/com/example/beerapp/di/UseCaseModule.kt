package com.example.beerapp.di

import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import com.example.beerapp.features.beerCatalog.domain.usecases.ClearDatabaseUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.GetBeerDetailUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.GetBeerFavouriteUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.GetBeerListFromApiUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.GetBeerListFromDatabaseUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.SaveBeersToDatabaseUseCase
import com.example.beerapp.features.beerCatalog.domain.usecases.UpdateBeerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetBeerListFromApiUseCase(
        beerRepository: BeerRepository
    ): GetBeerListFromApiUseCase {
        return GetBeerListFromApiUseCase(beerRepository)
    }

    @Provides
    @Singleton
    fun provideGetBeerDetailUseCase(
        beerRepository: BeerRepository
    ): GetBeerDetailUseCase {
        return GetBeerDetailUseCase(beerRepository)
    }


    @Provides
    @Singleton
    fun provideClearDatabaseUseCase(
        beerRepository: BeerRepository
    ): ClearDatabaseUseCase {
        return ClearDatabaseUseCase(beerRepository)
    }


    @Provides
    @Singleton
    fun provideGetBeerListFromDatabaseUseCase(
        beerRepository: BeerRepository
    ): GetBeerListFromDatabaseUseCase {
        return GetBeerListFromDatabaseUseCase(beerRepository)
    }


    @Provides
    @Singleton
    fun provideSaveBeersToDatabaseUseCase(
        beerRepository: BeerRepository
    ): SaveBeersToDatabaseUseCase {
        return SaveBeersToDatabaseUseCase(beerRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateBeerUseCase(
        beerRepository: BeerRepository
    ): UpdateBeerUseCase {
        return UpdateBeerUseCase(beerRepository)
    }

    @Provides
    @Singleton
    fun provideGetBeerFavouriteUseCase(
        beerRepository: BeerRepository
    ): GetBeerFavouriteUseCase {
        return GetBeerFavouriteUseCase(beerRepository)
    }

}