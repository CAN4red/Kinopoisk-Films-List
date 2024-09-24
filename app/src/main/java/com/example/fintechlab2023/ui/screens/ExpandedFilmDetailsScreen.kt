package com.example.fintechlab2023.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fintechlab2023.model.Country
import com.example.fintechlab2023.model.FilmDetails
import com.example.fintechlab2023.model.Genre

@Composable
fun ExpandedFilmDetailsScreen(
    filmDetailsUiState: FilmDetailsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (filmDetailsUiState) {
        is FilmDetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is FilmDetailsUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )

        is FilmDetailsUiState.Success -> ExpandedFilmDetails(
            filmDetails = filmDetailsUiState.filmDetails,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ExpandedFilmDetails(
    filmDetails: FilmDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        val descriptor = filmDetails.description
        val genres = filmDetails.genres.joinToString(separator = ", ") { it.genre }
        val countries = filmDetails.countries.joinToString(separator = ", ") {it.country}
        val year = filmDetails.year.toString()

        Text(text = descriptor)
        Text(text = genres)
        Text(text = countries)
        Text(text = year)

        if (filmDetails.rating != null) {
            val rating = filmDetails.rating.toString()
            Text(text = rating)
        }

    }
}

@Composable
@Preview
fun ExpandedFilmDetailsPreview() {
    ExpandedFilmDetails(filmDetails = filmDetails)
}

val filmDetails = FilmDetails(
    id = 1,
    name = "Битлджус Битлджус",
    posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/623807.jpg",
    rating = 7.1,
    year = 2024,
    description = "После смерти отца Лидия со своей дочерью Астрид и мачехой Делией " +
            "возвращаются в старый дом в городке Уинтер-Ривер. Когда Астрид обнаруживает " +
            "на чердаке тот самый макет города, Лидии приходится рассказать ей о Битлджусе " +
            "— озорном и крайне неприятном призраке, чье имя ни в коем случае нельзя " +
            "называть три раза. Но любопытство девочки берет верх — она открывает портал " +
            "в загробную жизнь. Битлджус начинает снова терроризировать всех живых.",
    countries = listOf(Country("США")),
    genres = listOf(Genre("фэнтези"), Genre("комедия"))
)
