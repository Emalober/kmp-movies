package com.emalober.kmpmovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emalober.kmpmovies.data.MoviesRepository
import com.emalober.kmpmovies.data.MoviesService
import com.emalober.kmpmovies.ui.screens.detail.DetailScreen
import com.emalober.kmpmovies.ui.screens.detail.DetailViewModel
import com.emalober.kmpmovies.ui.screens.home.HomeScreen
import com.emalober.kmpmovies.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kmpmovies.composeapp.generated.resources.Res
import kmpmovies.composeapp.generated.resources.themoviedb
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource

//import org.koin.compose.viewmodel.koinViewModel
//import org.koin.core.annotation.KoinExperimentalAPI
//import org.koin.core.parameter.parametersOf
//
//@OptIn(KoinExperimentalAPI::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val repository = rememberMoviesRepository()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                onMovieClick = { movie -> navController.navigate("detail/${movie.id}") },
                vm = viewModel { HomeViewModel(repository) }
            )
        }

        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                //vm = koinViewModel(parameters = { parametersOf(movieId) }),
                vm = viewModel { DetailViewModel(repository = repository, id = movieId) },
                onBack = { navController.popBackStack() })
        }
    }
}

@Composable
private fun rememberMoviesRepository(): MoviesRepository {
    val apiKey = stringResource(Res.string.themoviedb)
    val client = remember {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", apiKey)
                }
            }
        }
    }
    val repository = remember { MoviesRepository(MoviesService(client)) }
    return remember { repository }
}