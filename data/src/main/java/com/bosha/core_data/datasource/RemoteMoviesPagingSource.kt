package com.bosha.core_data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bosha.core_data.datasource.remote.RemoteDataSource
import com.bosha.core_domain.entities.Movie
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

class RemoteMoviesPagingSource(
    private val dataSource: RemoteDataSource,
    private val pagesize: Int
) : PagingSource<Int, Movie>(), RemoteDataSource by dataSource {

    //перезагузка данных
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        return try {
            val movies = rawQuery(position)
            // suits for db pagination
//            val nextKey = if (movies.size == params.loadSize) {
//                position + (params.loadSize / pagesize) //load size may be x3 when initial
//            } else {
//                null
//            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movies.size < pagesize) null else position + 1
            )
        } catch (e: Exception) {
            logcat(LogPriority.ERROR) {
                "${this::class.java.simpleName} load method with error: ${e.message}"
            }
            LoadResult.Error(e)
        }
    }
}