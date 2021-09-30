package com.bosha.data.repositoryImpl

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

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieRepository {
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
            .map { Result.success(it) } }

    override suspend fun insertMovies(list: List<Movie>) {
        localDataSource.insertMovies(list)
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        localDataSource.insertFavoriteMovie(movie)
    }

    override suspend fun getMovie(id: String): Movie {
       return localDataSource.getMovie(id)
    }

    override suspend fun deleteFavorite(id: String) {
        localDataSource.deleteFavorite(id)
    }

    override fun getMovieDetails(id: String): Flow<Result<MovieDetails>> {
        return remoteDataSource.getDetails(id)
            .mapToResult()
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
}