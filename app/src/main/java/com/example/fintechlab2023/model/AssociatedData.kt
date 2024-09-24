package com.example.fintechlab2023.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val country: String,
)

@Serializable
data class Genre(
    val genre: String,
)