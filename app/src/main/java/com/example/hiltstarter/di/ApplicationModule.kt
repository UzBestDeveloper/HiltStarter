package com.example.hiltstarter.di

import android.content.Context
import com.example.hiltstarter.utils.sharedPref.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun providePreferencesManager(@ApplicationContext context: Context) =
        PreferencesManager(context)

}