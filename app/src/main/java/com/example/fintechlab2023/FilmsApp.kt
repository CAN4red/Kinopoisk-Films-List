package com.example.fintechlab2023

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fintechlab2023.ui.screens.FilmViewModel
import com.example.fintechlab2023.data.navigation.AppScreen
import com.example.fintechlab2023.ui.screens.EmptyScreen
import com.example.fintechlab2023.ui.screens.ExpandedFilmDetailsScreen
import com.example.fintechlab2023.ui.screens.FilmDetailsScreen
import com.example.fintechlab2023.ui.screens.ListScreen

@Composable
fun FilmsApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController()
) {
    Scaffold() { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val filmViewModel: FilmViewModel = viewModel(factory = FilmViewModel.Factory)

            when (windowSize) {
                WindowWidthSizeClass.Compact -> CompactWindowLayout(
                    navController = navController,
                    filmViewModel = filmViewModel
                )

                WindowWidthSizeClass.Medium -> MediumWindowLayout(
                    navController = navController,
                    filmViewModel = filmViewModel
                )

                WindowWidthSizeClass.Expanded -> MediumWindowLayout(
                    navController = navController,
                    filmViewModel = filmViewModel
                )
            }
        }
    }
}

@Composable
fun CompactWindowLayout(
    navController: NavHostController,
    filmViewModel: FilmViewModel
) {
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

@Composable
fun MediumWindowLayout(
    navController: NavHostController,
    filmViewModel: FilmViewModel
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        ListScreen(
            listUiState = filmViewModel.listUiState.collectAsStateWithLifecycle().value,
            retryAction = filmViewModel::getFilms,
            onCardClick = { filmId ->
                filmViewModel.chooseFilm(filmId)
                when (navController.currentDestination?.route) {
                    AppScreen.FilmDetails.name -> navController.popBackStack()
                    AppScreen.ExpandedFilmDetails.name -> repeat(2) { navController.popBackStack() }
                }
                navController.navigate(AppScreen.FilmDetails.name)
            },
            modifier = Modifier.weight(1f)
        )

        NavHost(
            navController = navController,
            startDestination = AppScreen.FilmsList.name,
            modifier = Modifier
                .widthIn(max = (0.5 * LocalConfiguration.current.screenWidthDp).dp)
                .fillMaxHeight()
        ) {
            composable(route = AppScreen.FilmsList.name) {
                EmptyScreen()
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
