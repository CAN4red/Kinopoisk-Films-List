package com.example.fintechlab2023.data

import com.example.fintechlab2023.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val filmsRepository: FilmsRepository
}

class DefaultAppContainer : AppContainer {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    override val filmsRepository: FilmsRepository by lazy {
        NetworkFilmsRepository(apiService = apiService)
    }

    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    }
}