package com.example.beerapp.features.beerCatalog.data.datasources.api.localDatabase

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {
    @Query("SELECT * FROM beer_table")
    fun getAllBeers(): Flow<List<BeerEntity>>

    @Upsert
    suspend fun insertAll(beers: List<BeerEntity>)

    @Query("DELETE FROM beer_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM beer_table WHERE isFavourite = 1")
    fun getFavourites(): Flow<List<BeerEntity>>

    @Update
    suspend fun updateBeer(beer: BeerEntity)
}