package com.emalober.kmpmovies.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emalober.kmpmovies.data.Movie
import com.emalober.kmpmovies.data.MoviesRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Int,
    private val repository: MoviesRepository
) : ViewModel() {

    var state: UiState by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {

            state = state.copy(loading = true)
            repository.fetchMovieById(id).collect {
                it?.let { state = UiState(loading = false, movie = it) }
            }
        }
    }

    fun onFavoriteClick() {
        state.movie?.let {
            viewModelScope.launch {
                repository.toggleFavorite(it)
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )

}