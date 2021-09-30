package com.bosha.data.repositoryImpl

import com.bosha.data.remote.RemoteDataSource
import com.bosha.domain.entities.Movie
import com.bosha.domain.repositories.MovieRepository
import com.bosha.domain.repositories.MoviesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    override fun fetchMovies(): Flow<MoviesResult> {
       return remoteDataSource.fetchMovies()
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