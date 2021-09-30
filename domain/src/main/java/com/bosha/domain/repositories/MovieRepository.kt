package com.bosha.domain.repositories

import com.bosha.domain.entities.Movie
import com.bosha.domain.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

typealias MoviesResult = Result<List<Movie>>

interface MovieRepository {
    fun fetchMovies(): Flow<MoviesResult>

    fun getCachedMovies(): Flow<MoviesResult>

    suspend fun insertMovies(list: List<Movie>)

    fun getMovieDetails(id: String): Flow<Result<MovieDetails>>
}