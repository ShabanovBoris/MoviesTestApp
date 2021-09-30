package com.bosha.domain.repositories

import com.bosha.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

typealias MoviesResult = Result<List<Movie>>

interface MovieRepository {
    fun fetchMovies(): Flow<MoviesResult>
}