package com.bosha.core_data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bosha.core_data.datasource.local.LocalDataSource
import com.bosha.core_data.datasource.remote.RemoteDataSource
import com.bosha.core_data.dto.local.MovieEntity
import java.io.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
//        filter: SomeFilterType(Int,String) pass a filter if need
) : RemoteMediator<Int, MovieEntity>() {

  private var pageIndex = 0

  override suspend fun load(
      loadType: LoadType,
      state: PagingState<Int, MovieEntity>
  ): MediatorResult {

    pageIndex =
        when (loadType) {
          LoadType.REFRESH -> 1
          // prepend not needed
          LoadType.PREPEND ->
              return MediatorResult.Success(endOfPaginationReached = true) // not needed
          LoadType.APPEND -> ++pageIndex
        //            LoadType.APPEND -> {
        //                val lastItem = state.lastItemOrNull()
        //                    ?: return MediatorResult.Success(endOfPaginationReached = true)
        //                lastItem.page ?: 0 + 1
        //            }
        }

    return try {

      val response = remoteDataSource.rawQuery(pageIndex)

      if (loadType == LoadType.REFRESH) {
        localDataSource.refresh(response)
      } else {
        localDataSource.insertMovies(response)
      }

      MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)
    } catch (e: IOException) {
      MediatorResult.Error(e)
    } catch (e: HttpException) {
      MediatorResult.Error(e)
    }
  }
}
