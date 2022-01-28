package com.bosha.data.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bosha.data.datasource.local.LocalDataSource
import com.bosha.data.datasource.remote.RemoteDataSource
import com.bosha.data.dto.local.MovieEntity
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
@Deprecated("Доработать или удалить")
class MoviesRemoteMediator(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RemoteMediator<Int, MovieEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        val nextPage = when (loadType) {
            LoadType.REFRESH -> 1
            // prepend not needed
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true) // not needed
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                lastItem.page ?: 0 + 1
            }
        }

        return try {

            val response = remoteDataSource.rawQuery(nextPage)

            if (loadType == LoadType.REFRESH) {
                localDataSource.clearData()
                Log.e("TAG", "load: clearData")
            }

            localDataSource.insertMovies(response)

            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

