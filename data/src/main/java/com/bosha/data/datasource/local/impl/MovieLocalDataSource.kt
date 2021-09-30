package com.bosha.data.datasource.local.impl

import com.bosha.data.datasource.local.LocalDataSource
import com.bosha.data.datasource.local.MovieDao
import com.bosha.data.datasource.remote.MovieNetworkApi
import com.bosha.data.mappers.MovieDBEntityMapper
import com.bosha.data.mappers.MovieResponseMapper
import com.bosha.domain.entities.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val dao: MovieDao,
    private val mapper: MovieDBEntityMapper,
    private val dispatcher: CoroutineDispatcher? = null
): LocalDataSource {

    override suspend fun insertMovies(list: List<Movie>) {
        dao.insertMovies(mapper.toMovieEntityList(list))
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        dao.insertFavoriteMovie(mapper.toFavoriteMovie(movie))
    }

    override suspend fun getMovie(id: String): Movie {
       return mapper.toMovie(dao.getById(id))
    }

    override suspend fun deleteFavorite(id: String) {
        dao.deleteFromFavorite(id)
    }

    override fun getMovies(): Flow<List<Movie>> =
        dao.getMovies().map { mapper.toMovieList(it) }
            .flowOn(dispatcher ?: Dispatchers.Main.immediate)

    override fun getFavoritesMovies(): Flow<List<Movie>> =
        dao.getFavoritesMovies().map { it.map { item -> mapper.toMovie(item) } }
            .flowOn(dispatcher ?: Dispatchers.Main.immediate)
}