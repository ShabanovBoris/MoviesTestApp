package com.bosha.data.datasource.local

import androidx.paging.PagingData
import com.bosha.data.datasource.remote.RemoteDataSource
import com.bosha.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getMovies(): Flow<List<Movie>>

    fun getMoviesPaging(mediator: RemoteDataSource): Flow<PagingData<Movie>>

    fun getFavoritesMovies(): Flow<List<Movie>>

    fun searchByTitleFromCache(title: String): Flow<List<Movie>>

    suspend fun clearData()

    suspend fun insertMovies(list: List<Movie>)

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun getMovie(id: String): Movie

    suspend fun deleteFavorite(id: String)
}