package com.bosha.core_data.mappers

import com.bosha.core.Mapper
import com.bosha.core_data.datasource.remote.impl.MoviesRemoteDataSource.Companion.baseImageBackdropUrl
import com.bosha.core_data.dto.remote.JsonMovieDetails
import com.bosha.core_domain.entities.Actor
import com.bosha.core_domain.entities.Genre
import com.bosha.core_domain.entities.MovieDetails
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