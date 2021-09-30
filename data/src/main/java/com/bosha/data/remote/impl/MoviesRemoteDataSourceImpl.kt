package com.bosha.data.remote.impl

import com.bosha.data.dto.remote.JsonMovie
import com.bosha.data.mappers.MovieResponseMapper
import com.bosha.data.remote.MovieNetworkApi
import com.bosha.data.remote.RemoteDataSource
import com.bosha.domain.entities.Genre
import com.bosha.domain.entities.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.internal.toImmutableMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceImpl @Inject constructor(
    private val api: MovieNetworkApi,
    private val mapper: MovieResponseMapper,
    private val dispatcher: CoroutineDispatcher? = null
) : RemoteDataSource {
    companion object {
        const val baseImagePosterUrl = "https://image.tmdb.org/t/p/w500"
        const val baseImageBackdropUrl = "https://image.tmdb.org/t/p/w780"
    }

    private var genresMap = emptyMap<Int,Genre>()

    override fun fetchMovies(): Flow<List<Movie>> =
        flow {
            getMoviesByPage(1..1){
                emit(it)
            }
        }
            .onStart { if (genresMap.isEmpty()) getGenresMap() }
            .map { mapper.toMovieList(it, genresMap) }
            .flowOn(dispatcher ?: Dispatchers.Main.immediate)


    private suspend fun getGenresMap(){
        val mutableMap: MutableMap<Int, Genre> = mutableMapOf()
        api.getGenres().genres.forEach {
            mutableMap[it.id] = Genre(it.name)
        }
        genresMap = mutableMap.toImmutableMap()
    }
    private suspend fun getMoviesByPage(range: IntRange, init: suspend (List<JsonMovie>) -> Unit){
        range.forEach {
            init(api.fetchMovies(it).results)
        }
    }
}