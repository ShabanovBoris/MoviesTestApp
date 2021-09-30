package com.bosha.domain.repositories

import com.bosha.domain.entities.Movie
import com.bosha.domain.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

typealias MoviesResult = Result<List<Movie>>

interface MovieRepository {
    fun fetchMovies(): Flow<MoviesResult>

    fun getCachedMovies(): Flow<MoviesResult>

    fun getFavoritesMovies(): Flow<MoviesResult>

    suspend fun insertMovies(list: List<Movie>)

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun getMovie(id: String): Movie

    suspend fun deleteFavorite(id: String)

    fun getMovieDetails(id: String): Flow<Result<MovieDetails>>
}