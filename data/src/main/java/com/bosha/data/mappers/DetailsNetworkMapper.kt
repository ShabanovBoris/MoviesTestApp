package com.bosha.data.mappers

import com.bosha.data.datasource.remote.impl.MoviesRemoteDataSource.Companion.baseImageBackdropUrl
import com.bosha.data.dto.remote.JsonMovieDetails
import com.bosha.domain.entities.Actor
import com.bosha.domain.entities.Genre
import com.bosha.domain.entities.MovieDetails
import com.bosha.domain.utils.Mapper
import javax.inject.Inject

class DetailsNetworkMapper @Inject constructor(): Mapper<JsonMovieDetails, MovieDetails> {

    var actors: List<Actor>? = null

    override fun toDomainEntity(data: JsonMovieDetails, page: Int?): MovieDetails = MovieDetails(
    id = data.id,
    title = data.title,
    overview = data.overview ?: "",
    runtime = data.runtime ?: 0,
    imageBackdrop = baseImageBackdropUrl + data.backdropPath,
    genres = data.genres.map { genre -> Genre(genre.name) },
    actors = requireNotNull(actors),
    votes = data.votes / 2
    )

    override fun toDataEntity(domain: MovieDetails): JsonMovieDetails {
        throw NotImplementedError()
    }
}