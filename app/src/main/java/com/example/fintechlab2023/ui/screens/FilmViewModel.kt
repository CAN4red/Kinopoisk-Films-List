package com.example.fintechlab2023.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechlab2023.data.FilmsRepository
import com.example.fintechlab2023.model.Film
import com.example.fintechlab2023.model.FilmDetails
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ListUiState {
    data class Success(val films: List<Film>) : ListUiState
    data object Error : ListUiState
    data object Loading : ListUiState
}

sealed interface FilmDetailsUiState {
    data class Success(val filmDetails: FilmDetails) : FilmDetailsUiState
    data object Error : FilmDetailsUiState
    data object Loading : FilmDetailsUiState
}

class FilmViewModel(
    private val filmsRepository: FilmsRepository,
) : ViewModel() {

    var listUiState: ListUiState by mutableStateOf(ListUiState.Loading)
        private set

    var filmDetailsUiState: FilmDetailsUiState by mutableStateOf(FilmDetailsUiState.Loading)
        private set

    private var chosenFilmId: Int? by mutableStateOf(null)

    init {
        getFilms()
    }

    fun getFilms() {
        viewModelScope.launch {
            listUiState = ListUiState.Loading
            listUiState = try {
                ListUiState.Success(filmsRepository.getFilms())
            } catch (e: IOException) {
                ListUiState.Error
            } catch (e: HttpException) {
                ListUiState.Error
            }
        }
    }

    fun choseFilm(id: Int) {
        chosenFilmId = id
        getFilmDetails()
    }

    fun getFilmDetails() {
        viewModelScope.launch {
            filmDetailsUiState = FilmDetailsUiState.Loading
            filmDetailsUiState = try {
                FilmDetailsUiState.Success(filmsRepository.getFilmDetails(chosenFilmId ?: 0))
            } catch (e: IOException) {
                FilmDetailsUiState.Error
            } catch (e: HttpException) {
                FilmDetailsUiState.Error
            }
        }
    }
}