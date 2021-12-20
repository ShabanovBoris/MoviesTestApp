package com.bosha.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bosha.data.datasource.RemoteMoviesPagingSource
import com.bosha.data.datasource.local.LocalDataSource
import com.bosha.data.datasource.remote.RemoteDataSource
import com.bosha.domain.entities.Movie
import com.bosha.domain.entities.MovieDetails
import com.bosha.domain.repositories.MovieRepository
import com.bosha.domain.repositories.MoviesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryMovieImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : MovieRepository, PagingRepository {
    override fun fetchMovies(): Flow<MoviesResult> {
        return remoteDataSource.fetchMovies()
            .mapToResult()
    }

    override fun getCachedMovies(): Flow<MoviesResult> {
        return localDataSource.getMovies()
            .map { Result.success(it) }
    }

    override fun getFavoritesMovies(): Flow<MoviesResult> {
        return localDataSource.getFavoritesMovies()
            .map { Result.success(it) }
    }

    override suspend fun insertCachedMovies(list: List<Movie>) {
        localDataSource.insertMovies(list)
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        localDataSource.insertFavoriteMovie(movie)
    }

    override suspend fun getCachedMovie(id: String): Movie {
        return localDataSource.getMovie(id)
    }

    override suspend fun deleteFavorite(id: String) {
        localDataSource.deleteFavorite(id)
    }

    override fun fetchMovieDetails(id: String): Flow<Result<MovieDetails>> {
        return remoteDataSource.getDetails(id)
            .mapToResult()
    }

    override fun searchByTitle(title: String): Flow<MoviesResult> {
        return remoteDataSource.searchByTitle(title)
            .mapToResult()
    }

    override fun searchByTitleFromCache(title: String): Flow<MoviesResult> {
        return localDataSource.searchByTitleFromCache(title)
            .map { Result.success(it) }
    }

    private fun <T> Flow<T>.mapToResult(): Flow<Result<T>> =
        map { Result.success(it) }
            .catch { cause ->
                if ((cause is retrofit2.HttpException) ||
                    (cause is TimeoutException) ||
                    (cause is UnknownHostException)
                )
                    emit(Result.failure(cause as Exception))
                else throw cause
            }

    override fun fetchMoviesPaging(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                1,
                enablePlaceholders = true,
                prefetchDistance = 1,
                initialLoadSize = 1
            ),
            pagingSourceFactory = { RemoteMoviesPagingSource(remoteDataSource) }
        ).flow

    override fun getMoviesPaging(): Flow<PagingData<Movie>> = localDataSource.getMoviesPaging(remoteDataSource)
}