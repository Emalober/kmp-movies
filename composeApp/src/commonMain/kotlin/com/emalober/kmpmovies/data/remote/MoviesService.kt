package com.emalober.kmpmovies.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

const val DISCOVER_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie"

class MoviesService(private val client: HttpClient) {

    suspend fun fetchPopularMovies(region: String): RemoteResult {
        return client.get("/3/discover/movie") {
            parameter("sort_by", "popularity.desc")
            parameter("region", region)
        }
            .body<RemoteResult>()
    }

    suspend fun fetchMovieById(id: Int): RemoteMovie = client.get("/3/movie/$id").body<RemoteMovie>()
}