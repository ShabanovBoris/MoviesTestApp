package com.bosha.data.repositories

import androidx.paging.PagingData
import com.bosha.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface PagingRepository {

    fun fetchMoviesPaging(): Flow<PagingData<Movie>>

    fun getMoviesPaging(): Flow<PagingData<Movie>>

}
