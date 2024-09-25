package com.example.fintechlab2023.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    backAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box {
        IconButton(
            onClick = backAction,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Button",
                tint = Color.Gray.copy(alpha = 0.8f)
            )
        }

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
}

@Composable
fun ExpandedFilmDetails(
    filmDetails: FilmDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            top = 10.dp,
            bottom = 20.dp,
            start = 32.dp,
            end = 32.dp
        )
    ) {
        val description = filmDetails.description
        val genres = filmDetails.genres.joinToString(separator = ", ") { it.genre }
        val countries = filmDetails.countries.joinToString(separator = ", ") { it.country }
        val year = filmDetails.year.toString()

        val paddingModifier = Modifier.padding(bottom = 16.dp)

        Text(
            text = "Описание",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight(900),
            modifier = paddingModifier.align(Alignment.CenterHorizontally)
        )

        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight(500),
                modifier = paddingModifier
            )
        }
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
