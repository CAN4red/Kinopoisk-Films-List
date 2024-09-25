package com.example.fintechlab2023.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fintechlab2023.R
import com.example.fintechlab2023.model.FilmDetails

@Composable
fun FilmDetailsScreen(
    filmDetailsUiState: FilmDetailsUiState,
    retryAction: () -> Unit,
    moreDetailsAction: () -> Unit,
    modifier: Modifier = Modifier,
    backAction: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (filmDetailsUiState) {
            is FilmDetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is FilmDetailsUiState.Error -> ErrorScreen(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )

            is FilmDetailsUiState.Success -> FilmDetails(
                filmDetails = filmDetailsUiState.filmDetails,
                moreDetailsAction = moreDetailsAction,
                modifier = modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = backAction,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Button",
                tint = Color.Gray.copy(alpha = 0.8f)
            )
        }
    }
}


@Composable
fun FilmDetails(
    filmDetails: FilmDetails,
    moreDetailsAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(filmDetails.posterUrl)
                .build(),
            contentDescription = "Film Description",
            error = painterResource(R.drawable.error_img),
            placeholder = painterResource(R.drawable.loading_img),
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = Modifier.padding(
                vertical = 20.dp,
                horizontal = 32.dp
            )
        ) {
            Text(
                text = filmDetails.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight(900),
                modifier = Modifier.padding(bottom = 16.dp),
            )

            filmDetails.description?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight(500),
                    color = LocalContentColor.current.copy(alpha = 0.6f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }

            ClickableText(
                text = "Все детали о фильме",
                onClick = moreDetailsAction,
            )

        }
    }
}

@Composable
private fun ClickableText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        textDecoration = TextDecoration.Underline,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight(950),
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
    )
}
