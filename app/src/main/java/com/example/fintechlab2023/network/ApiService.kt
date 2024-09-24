package com.example.fintechlab2023.network

import com.example.fintechlab2023.model.FilmDetails
import com.example.fintechlab2023.model.FilmsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers("X-API-KEY: $API_KEY")
    @GET("$ROUTE_LIST_OF_FILMS?type=$TYPE")
    suspend fun getFilms(): Response<FilmsResponse>

    @Headers("X-API-KEY: $API_KEY")
    @GET("$ROUTE_EXACT_FILM/{id}")
    suspend fun getFilmDetails(@Path("id") id: Int): Response<FilmDetails>

    companion object {
        private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
        private const val ROUTE_LIST_OF_FILMS = "api/v2.2/films/top"
        private const val ROUTE_EXACT_FILM = "api/v2.2/films"
        private const val TYPE = "TOP_100_POPULAR_FILMS"
    }
}
