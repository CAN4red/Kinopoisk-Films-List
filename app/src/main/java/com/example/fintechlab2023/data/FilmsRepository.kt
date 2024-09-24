package com.example.fintechlab2023.data

import com.example.fintechlab2023.model.Film
import com.example.fintechlab2023.model.FilmDetails
import com.example.fintechlab2023.network.ApiService

interface FilmsRepository {
    suspend fun getFilms(): List<Film>
    suspend fun getFilmDetails(id: Int): FilmDetails
}

class NetworkFilmsRepository(
    private val apiService: ApiService
) : FilmsRepository {

    override suspend fun getFilms(): List<Film> {
        return apiService.getFilms().body()?.films ?: listOf()
    }

    override suspend fun getFilmDetails(id: Int): FilmDetails {
        return apiService.getFilmDetails(id).body()
            ?: throw Exception("Error fetching film details")
    }
}