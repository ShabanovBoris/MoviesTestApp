package com.bosha.core_data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bosha.core_data.datasource.remote.RemoteDataSource
import com.bosha.core_domain.entities.Movie
import javax.inject.Inject

class RemoteMoviesPagingSource @Inject constructor(
    private val dataSource: RemoteDataSource
) : PagingSource<Int, Movie>(), RemoteDataSource by dataSource {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        while (true) {
            try {
                val movies = dataSource.rawQuery(position)
                val nextKey = if (movies.isEmpty()) {
                    null
                } else {
                    position + params.loadSize
                }
                return LoadResult.Page(
                    data = movies,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = nextKey
                )
            } catch (e: Exception) {
                Log.e(this.toString(), "load: ${e.message}")
                return LoadResult.Error(e)
            }
        }

        error(IllegalStateException())
    }
}