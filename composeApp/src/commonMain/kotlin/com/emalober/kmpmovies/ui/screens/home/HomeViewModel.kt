package com.emalober.kmpmovies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emalober.kmpmovies.data.Movie
import com.emalober.kmpmovies.data.MoviesRepository
import com.emalober.kmpmovies.data.RemoteMovie
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(
                loading = false,
                movies = repository.fetchPopularMovies()
            )
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )

}