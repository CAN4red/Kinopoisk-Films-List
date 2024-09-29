package com.example.fintechlab2023.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.fintechlab2023.R
import com.example.fintechlab2023.model.Country
import com.example.fintechlab2023.model.Film
import com.example.fintechlab2023.model.Genre

@Composable
fun ListScreen(
    listUiState: ListUiState,
    retryAction: () -> Unit,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (listUiState) {
        is ListUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ListUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )

        is ListUiState.Success -> FilmsColumnScreen(
            films = listUiState.films,
            contentPadding = contentPadding,
            onCardClick = onCardClick,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = LocalContentColor.current,
            strokeWidth = 8.dp,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_img),
            contentDescription = ""
        )
        Text(text = "Failed to Load", modifier = Modifier.padding(16.dp))

        Button(onClick = retryAction) { Text(text = "Retry") }
    }
}

@Composable
fun FilmsColumnScreen(
    films: List<Film>,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = films, key = { film -> film.id }) { film ->
            FilmCard(
                film = film,
                modifier = modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    )
                    .clickable(onClick = { onCardClick(film.id) })
            )
        }
    }
}

@Composable
fun FilmCard(film: Film, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(3f),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(film.posterUrlPreview)
                    .transformations(RoundedCornersTransformation(16f))
                    .build(),
                contentDescription = "Film Preview",
                error = painterResource(R.drawable.error_img),
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier
                    .size(width = 90.dp, height = 120.dp)
                    .padding(12.dp),
            )

            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = film.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight(700),
                    modifier = Modifier.padding(2.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                val mainGenre = film.genres.first().genre.replaceFirstChar { it.uppercase() }
                val year = film.year
                Text(
                    text = "$mainGenre ($year)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight(700),
                    color = LocalContentColor.current.copy(alpha = 0.6f),
                    modifier = Modifier.padding(2.dp),
                )
            }
        }
    }
}

@Composable
@Preview
fun LoadingScreenPreview() {
    LoadingScreen(modifier = Modifier.fillMaxSize())
}

@Composable
@Preview
fun FilmCardPreview() {
    FilmCard(
        film = previewFilm,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1.5f)
    )
}

private val previewFilm = Film(
    id = 1,
    name = "Лунтик: Возвращение домой",
    year = "2024",
    filmLength = "01:18",
    countries = listOf(Country("Poccия")),
    genres = listOf(
        Genre("фантастика"),
        Genre("приключения"),
        Genre("мультфильм"),
        Genre("детский")
    ),
    posterUrlPreview = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/5307156.jpg",
)