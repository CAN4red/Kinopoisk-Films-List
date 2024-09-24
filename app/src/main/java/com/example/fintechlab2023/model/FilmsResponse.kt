package com.example.fintechlab2023.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmsResponse(
    val pagesCount: Int,
    val films: List<Film>
)

@Serializable
data class Film(
    @SerialName(value = "filmId")
    val id: Int,
    @SerialName(value = "nameRu")
    val name: String,
    val year: String,
    val filmLength: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val posterUrlPreview: String,
)
