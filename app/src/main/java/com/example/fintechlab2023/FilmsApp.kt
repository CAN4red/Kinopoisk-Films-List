package com.example.fintechlab2023

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fintechlab2023.ui.screens.FilmViewModel
import com.example.fintechlab2023.data.DefaultAppContainer
import com.example.fintechlab2023.data.navigation.AppScreen
import com.example.fintechlab2023.ui.screens.ExpandedFilmDetailsScreen
import com.example.fintechlab2023.ui.screens.FilmDetailsScreen
import com.example.fintechlab2023.ui.screens.ListScreen

@Composable
fun FilmsApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold() { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val appContainer = DefaultAppContainer()
            val filmViewModel by remember {
                mutableStateOf(
                    FilmViewModel(
                        filmsRepository = appContainer.filmsRepository,
                    )
                )
            }

            NavHost(
                navController = navController,
                startDestination = AppScreen.FilmsList.name
            ) {
                composable(route = AppScreen.FilmsList.name) {
                    ListScreen(
                        listUiState = filmViewModel.listUiState,
                        retryAction = filmViewModel::getFilms,
                        onCardClick = { filmId ->
                            filmViewModel.choseFilm(filmId)
                            navController.navigate(AppScreen.FilmDetails.name)
                        },
                        contentPadding = innerPadding,
                    )
                }
                composable(route = AppScreen.FilmDetails.name) {
                    FilmDetailsScreen(
                        filmDetailsUiState = filmViewModel.filmDetailsUiState,
                        retryAction = filmViewModel::getFilmDetails,
                        moreDetailsAction = { navController.navigate(AppScreen.ExpandedFilmDetails.name) }
                    )
                }
                composable(route = AppScreen.ExpandedFilmDetails.name) {
                    ExpandedFilmDetailsScreen(
                        filmDetailsUiState = filmViewModel.filmDetailsUiState,
                        retryAction = filmViewModel::getFilmDetails
                    )
                }
            }
        }
    }
}