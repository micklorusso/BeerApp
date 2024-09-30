package com.example.beerapp.features.beerCatalog.data.repositories

import com.example.beerapp.features.beerCatalog.data.datasources.api.BeerApi
import com.example.beerapp.features.beerCatalog.data.datasources.api.localDatabase.BeerDao
import com.example.beerapp.features.beerCatalog.data.models.BeerModel
import com.example.beerapp.features.beerCatalog.domain.entities.BeerEntity
import com.example.beerapp.features.beerCatalog.domain.params.BeerDetailParams
import com.example.beerapp.features.beerCatalog.domain.repositories.BeerRepository
import com.example.beerapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val api: BeerApi,
    private val dao: BeerDao
): BeerRepository {
    override suspend fun getBeerListFromApi(): Resource<List<BeerEntity>> {
        return try {
            val response = api.getBeerList()
            val result = response.body()
            if(response.isSuccessful && result != null) {
                val beerEntities: MutableList<BeerEntity> = mutableListOf()
                for(elem in result){
                        beerEntities.add(BeerEntity(
                        id = elem.id,
                        image = elem.image,
                        name = elem.name,
                        price = elem.price,
                        isFavourite = false
                    ))
                }

                Resource.Success(beerEntities)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "GET /beers/ale: an error occurred")
        }
    }

    override suspend fun getBeerDetail(params: BeerDetailParams): Resource<BeerModel> {
        return try {
            val response = api.getBeerDetail(params.id)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "GET /beers/ale/id: an error occurred")
        }
    }

    override fun getBeerListFromDatabase(): Flow<List<BeerEntity>> {
        return dao.getAllBeers()
    }

    override suspend fun saveBeersToDatabase(beers: List<BeerEntity>) {
        dao.insertAll(beers)
    }

    override suspend fun clearDatabase() {
        dao.deleteAll()
    }

    override fun getBeerFavourite(): Flow<List<BeerEntity>> {
        return dao.getFavourites()
    }

    override suspend fun updateBeer(beer: BeerEntity) {
        dao.updateBeer(beer)
    }
}



