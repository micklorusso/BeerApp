package com.example.beerapp.di

import android.app.Application
import androidx.room.Room
import com.example.beerapp.features.beerCatalog.data.datasources.api.localDatabase.BeerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(app: Application): BeerDatabase {
        return Room.databaseBuilder(
            app,
            BeerDatabase::class.java,
            "beer_db"
        ).build()
    }

}