package com.bosha.core_data.repositories

import androidx.paging.PagingData
import com.bosha.core_domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface PagingRepository {

    fun fetchMoviesPaging(): Flow<PagingData<Movie>>

    fun getMoviesPaging(): Flow<PagingData<Movie>>

}