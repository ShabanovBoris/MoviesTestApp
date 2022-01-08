package com.bosha.data.datasource.local.impl

import androidx.paging.*
import com.bosha.data.datasource.MoviesRemoteMediator
import com.bosha.data.datasource.local.LocalDataSource
import com.bosha.data.datasource.local.MovieDao
import com.bosha.data.datasource.remote.RemoteDataSource
import com.bosha.data.dto.local.FavoriteMovieEntity
import com.bosha.data.dto.local.MovieEntity
import com.bosha.domain.entities.Movie
import com.bosha.domain.utils.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val dao: MovieDao,
    private val dispatcher: CoroutineDispatcher? = null,
    private val movieMapper: Mapper<MovieEntity, Movie>,
    private val favoriteMapper: Mapper<FavoriteMovieEntity, Movie>,
) : LocalDataSource {

    override suspend fun insertMovies(list: List<Movie>) {
        dao.insertMovies(movieMapper.toDataEntityList(list))
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        dao.insertFavoriteMovie(favoriteMapper.toDataEntity(movie))
    }

    override suspend fun getMovie(id: String): Movie {
        return movieMapper.toDomainEntity(dao.getById(id), null)
    }

    override suspend fun deleteFavorite(id: String) {
        dao.deleteFromFavorite(id)
    }

    override fun getMovies(): Flow<List<Movie>> =
        dao.getMovies().map(movieMapper::toDomainEntityList)
            .flowOn(dispatcher ?: Dispatchers.Main.immediate)



    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviesPaging(mediator: RemoteDataSource): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                20,
                initialLoadSize = 20
            ),
            pagingSourceFactory = dao::getMoviesPaging ,
            remoteMediator = MoviesRemoteMediator(mediator, this)
        )
            .flow
            .map { it.map { movieMapper.toDomainEntity(it, null) } }

    override fun getFavoritesMovies(): Flow<List<Movie>> =
        dao.getFavoritesMovies().map(favoriteMapper::toDomainEntityList)
            .flowOn(dispatcher ?: Dispatchers.Main.immediate)

    override fun searchByTitleFromCache(title: String): Flow<List<Movie>> =
        dao.getByTitle(title).map(movieMapper::toDomainEntityList)
            .flowOn(dispatcher ?: Dispatchers.Main.immediate)

    override suspend fun clearData() {
        dao.clear()
    }

}