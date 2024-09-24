package com.example.fintechlab2023.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        modifier = modifier.padding(
            vertical = 20.dp,
            horizontal = 32.dp
        )
    ) {

        val description = filmDetails.description
        val genres = filmDetails.genres.joinToString(separator = ", ") { it.genre }
        val countries = filmDetails.countries.joinToString(separator = ", ") { it.country }
        val year = filmDetails.year.toString()

        val paddingModifier = Modifier.padding(bottom = 16.dp)

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight(500),
            color = LocalContentColor.current.copy(alpha = 0.6f),
            modifier = paddingModifier
        )
        DisplayInfo("Жанры: ", genres, modifier = paddingModifier)
        DisplayInfo("Страны: ", countries, modifier = paddingModifier)
        DisplayInfo("Год: ", year, modifier = paddingModifier)

        filmDetails.rating?.let { rating ->
            DisplayInfo(label = "Рейтинг: ", value = rating.toString(), modifier = paddingModifier)
        }
    }
}

@Composable
fun DisplayInfo(label: String, value: String, modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) { append(label) }
            append(value)
        },
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight(500),
        color = LocalContentColor.current.copy(alpha = 0.6f),
        modifier = modifier
    )
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
