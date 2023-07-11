package com.example.hiltstarter.di

import com.example.hiltstarter.BuildConfig
import com.example.hiltstarter.network.api.ApiService
import com.example.hiltstarter.network.interceptors.AddCookiesInterceptor
import com.example.hiltstarter.network.interceptors.ReceivedCookiesInterceptor
import com.example.hiltstarter.utils.sharedPref.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        appInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        prefs: PreferencesManager,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(appInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AddCookiesInterceptor(prefs))
            .addInterceptor(ReceivedCookiesInterceptor(prefs))
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Singleton
    @Provides
    fun provideAppInterceptor(prefs: PreferencesManager): Interceptor {
        return Interceptor {
            val token: String = prefs.token
            val language: String = prefs.language

            val request = it.request().newBuilder()
                .addHeader(BuildConfig.AUTHORIZATION, "Bearer $token")
                .addHeader(BuildConfig.ACCEPT_LANGUAGE, language)
                .addHeader(BuildConfig.ACCEPT, BuildConfig.ACCEPT_APPLICATION_JSON)
                .addHeader("Content-Type", "application/json")
                .build()

            val response = it.proceed(request)

            response
        }
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create()



}