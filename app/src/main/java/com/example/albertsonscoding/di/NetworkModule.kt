package com.example.albertsonscoding.di

import com.example.albertsonscoding.model.remote.AcronymService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
@OptIn(ExperimentalSerializationApi::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetroFit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://www.nactem.ac.uk/software/acromine/dictionary.py/")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @Provides
    @Singleton
    fun providesAcronymService(retrofit: Retrofit): AcronymService = retrofit.create()
}
