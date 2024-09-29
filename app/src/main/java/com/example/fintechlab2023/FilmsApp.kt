package com.example.fintechlab2023

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fintechlab2023.ui.screens.FilmViewModel
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            val filmViewModel: FilmViewModel = viewModel(factory = FilmViewModel.Factory)

            NavHost(
                navController = navController,
                startDestination = AppScreen.FilmsList.name
            ) {
                composable(route = AppScreen.FilmsList.name) {
                    ListScreen(
                        listUiState = filmViewModel.listUiState.collectAsStateWithLifecycle().value,
                        retryAction = filmViewModel::getFilms,
                        onCardClick = { filmId ->
                            filmViewModel.chooseFilm(filmId)
                            navController.navigate(AppScreen.FilmDetails.name)
                        },
                    )
                }
                composable(route = AppScreen.FilmDetails.name) {
                    FilmDetailsScreen(
                        filmDetailsUiState = filmViewModel.filmDetailsUiState.collectAsStateWithLifecycle().value,
                        retryAction = filmViewModel::getFilmDetails,
                        moreDetailsAction = { navController.navigate(AppScreen.ExpandedFilmDetails.name) },
                        backAction = { navController.navigateUp() }
                    )
                }
                composable(route = AppScreen.ExpandedFilmDetails.name) {
                    ExpandedFilmDetailsScreen(
                        filmDetailsUiState = filmViewModel.filmDetailsUiState.collectAsStateWithLifecycle().value,
                        retryAction = filmViewModel::getFilmDetails,
                        backAction = { navController.navigateUp() }
                    )
                }
            }
        }
    }
}
