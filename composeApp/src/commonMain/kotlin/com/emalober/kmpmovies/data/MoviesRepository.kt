package com.emalober.kmpmovies.data

import com.emalober.kmpmovies.data.database.MoviesDao
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val moviesDao: MoviesDao,
    private val moviesService: MoviesService
) {

    val movies = moviesDao.fetchPopularMovies().onEach { movies ->
        if (movies.isEmpty()) {
            val popularMovies = moviesService.fetchPopularMovies().results.map { it.toDomain() }
            moviesDao.save(popularMovies)
        }
    }

    fun fetchMovieById(id: Int) = moviesDao.findMovieById(id).onEach { movie ->
        if (movie == null) {
            val remoteMovie = moviesService.fetchMovieById(id).toDomain()
            moviesDao.save(listOf(remoteMovie))
        }
    }

    suspend fun toggleFavorite(movie: Movie) {
        moviesDao.save(listOf(movie.copy(isFavorite = !movie.isFavorite)))
    }

    private fun RemoteMovie.toDomain() = Movie(
        id,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/w185/$posterPath",
        backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )
}