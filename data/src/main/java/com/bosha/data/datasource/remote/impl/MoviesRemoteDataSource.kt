package com.bosha.data.datasource.remote.impl

import com.bosha.data.datasource.remote.MovieNetworkApi
import com.bosha.data.datasource.remote.RemoteDataSource
import com.bosha.data.dto.remote.JsonActor
import com.bosha.data.dto.remote.JsonMovie
import com.bosha.data.dto.remote.JsonMovieDetails
import com.bosha.data.mappers.DetailsNetworkMapper
import com.bosha.data.mappers.MovieNetworkMapper
import com.bosha.domain.entities.Actor
import com.bosha.domain.entities.Genre
import com.bosha.domain.entities.Movie
import com.bosha.domain.entities.MovieDetails
import com.bosha.domain.utils.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.internal.toImmutableMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSource @Inject constructor(
    private val api: MovieNetworkApi,
    private val dispatcher: CoroutineDispatcher? = null,
    private val actorMapper: Mapper<JsonActor, Actor>,
    private val movieMapper: Mapper<JsonMovie, Movie>,
    private val detailsMapper: Mapper<JsonMovieDetails, MovieDetails>
) : RemoteDataSource {
    companion object {
        const val baseImagePosterUrl = "https://image.tmdb.org/t/p/w500"
        const val baseImageBackdropUrl = "https://image.tmdb.org/t/p/w780"
    }

    override suspend fun rawQuery(page: Int): List<Movie> {
        genresMap ?: getGenresMap()

        return getMoviesByPage(page)
            .map { movieMapper.toDomainEntity(it, page) }
    }



    private var genresMap
        get() = (movieMapper as MovieNetworkMapper).genresMap
        set(value) {
            (movieMapper as MovieNetworkMapper).genresMap = value
        }

    override fun fetchMovies(): Flow<List<Movie>> =
        flow {
            (1..5).forEach {
                getMoviesByPage(it) { list ->
                    emit(list)
                }
            }
        }
            .onStart { genresMap ?: getGenresMap() }
            .map(movieMapper::toDomainEntityList)
            .flowOn(dispatcher ?: Dispatchers.Main.immediate)

    override fun getDetails(id: String): Flow<MovieDetails> =
        flow { emit(api.getDetails(id)) }
            .zip(getCredits(id)) { details, actors ->
                (detailsMapper as DetailsNetworkMapper).actors = actors
                detailsMapper.toDomainEntity(details, null)
            }
            .flowOn(dispatcher ?: Dispatchers.Main.immediate)

    private fun getCredits(id: String): Flow<List<Actor>> =
        flow { emit(api.getCredits(id).cast) }
            .map(actorMapper::toDomainEntityList)

    override fun searchByTitle(title: String): Flow<List<Movie>> =
        flow { emit(api.getMovieBySearch(title).results) }
            .map(movieMapper::toDomainEntityList)

    private suspend fun getGenresMap() {
        val mutableMap: MutableMap<Int, Genre> = mutableMapOf()
        api.getGenres().genres.forEach {
            mutableMap[it.id] = Genre(it.name)
        }
        genresMap = mutableMap.toImmutableMap()
    }

    private suspend fun getMoviesByPage(
        page: Int,
        init: (suspend (List<JsonMovie>) -> Unit)? = null
    ): List<JsonMovie> {
        val response = api.fetchMovies(page).results
        init?.invoke(response)
        return response
    }
}