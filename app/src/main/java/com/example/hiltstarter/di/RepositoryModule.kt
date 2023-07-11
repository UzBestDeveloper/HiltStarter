package com.example.hiltstarter.di

import com.example.hiltstarter.ui.fragments.home.HomeRepository
import com.example.hiltstarter.ui.fragments.home.HomeRepositoryImpl
import com.example.hiltstarter.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideHomeRepository(apiService: ApiService): HomeRepository = HomeRepositoryImpl(apiService)


}