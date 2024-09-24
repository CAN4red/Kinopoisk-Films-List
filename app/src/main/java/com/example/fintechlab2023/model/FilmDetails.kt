package com.example.fintechlab2023.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDetails(
    @SerialName("kinopoiskId") val id: Int,
    @SerialName("nameRu") val name: String,
    val posterUrl: String,
    @SerialName("ratingKinopoisk") val rating: Double?,
    val year: Int,
    val description: String,
    val countries: List<Country>,
    val genres: List<Genre>,
)