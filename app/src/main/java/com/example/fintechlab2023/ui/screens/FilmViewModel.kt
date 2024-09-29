package com.example.fintechlab2023.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fintechlab2023.FilmsApplication
import com.example.fintechlab2023.data.FilmsRepository
import com.example.fintechlab2023.model.Film
import com.example.fintechlab2023.model.FilmDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private var _listUiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val listUiState: StateFlow<ListUiState>
        get() = _listUiState

    private var _filmDetailsUiState =
        MutableStateFlow<FilmDetailsUiState>(FilmDetailsUiState.Loading)
    val filmDetailsUiState: StateFlow<FilmDetailsUiState>
        get() = _filmDetailsUiState

    private var chosenFilmId: Int? by mutableStateOf(null)

    init {
        getFilms()
    }

    fun getFilms() {
        viewModelScope.launch {
            _listUiState.emit(ListUiState.Loading)
            _listUiState.emit(
                try {
                    ListUiState.Success(filmsRepository.getFilms())
                } catch (e: IOException) {
                    ListUiState.Error
                } catch (e: HttpException) {
                    ListUiState.Error
                }
            )
        }
    }

    fun chooseFilm(id: Int) {
        chosenFilmId = id
        getFilmDetails()
    }

    fun getFilmDetails() {
        viewModelScope.launch {
            _filmDetailsUiState.emit(FilmDetailsUiState.Loading)
            _filmDetailsUiState.emit(
                try {
                    FilmDetailsUiState.Success(
                        filmsRepository.getFilmDetails(chosenFilmId ?: 0)
                    )
                } catch (e: IOException) {
                    FilmDetailsUiState.Error
                } catch (e: HttpException) {
                    FilmDetailsUiState.Error
                }
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FilmsApplication)
                val filmsRepository = application.container.filmsRepository
                FilmViewModel(filmsRepository = filmsRepository)
            }
        }
    }
}
