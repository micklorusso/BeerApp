package com.example.beerapp.di

import com.example.beerapp.features.auth.data.service.AccountServiceImpl
import com.example.beerapp.features.auth.data.service.FirebaseServiceImpl
import com.example.beerapp.features.auth.data.service.StorageServiceImpl
import com.example.beerapp.features.auth.domain.AccountService
import com.example.beerapp.features.auth.domain.FirebaseService
import com.example.beerapp.features.auth.domain.StorageService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
  @Binds abstract fun provideFirebaseService(impl: FirebaseServiceImpl): FirebaseService
}
