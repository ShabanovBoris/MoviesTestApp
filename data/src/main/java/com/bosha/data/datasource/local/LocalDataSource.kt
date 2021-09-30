package com.bosha.data.datasource.local

import com.bosha.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getMovies(): Flow<List<Movie>>
    suspend fun insertMovies(list: List<Movie>)
}