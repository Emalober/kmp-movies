package com.emalober.kmpmovies.data

class MoviesRepository(private val moviesService: MoviesService) {

    suspend fun fetchPopularMovies(): List<Movie> {
        return moviesService.fetchPopularMovies().results.map { it.toDomain() }
    }

    suspend fun fetchMovieById(movieId: Int): Movie {
        return moviesService.fetchMovieById(movieId).toDomain()
    }

    private fun RemoteMovie.toDomain() = Movie(
        id = id,
        title = title,
        poster = "https://image.tmdb.org/t/p/w500$posterPath",
    )
}