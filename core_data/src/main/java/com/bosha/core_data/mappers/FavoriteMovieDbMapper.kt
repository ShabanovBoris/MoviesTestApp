package com.bosha.core_data.mappers

import com.bosha.core.Mapper
import com.bosha.core_data.dto.local.FavoriteMovieEntity
import com.bosha.core_data.dto.local.GenreEntity
import com.bosha.core_domain.entities.Genre
import com.bosha.core_domain.entities.Movie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FavoriteMovieDbMapper @Inject constructor(): Mapper<FavoriteMovieEntity, Movie> {

    override fun toDomainEntity(data: FavoriteMovieEntity, page: Int?): Movie =
        Movie(
            id = data.id,
            title = data.title,
            genres = (Json.decodeFromString(data.genres) as List<GenreEntity>)
                .map { genre -> Genre(genre.name) },
            rating = data.rating,
            imageUrl = data.imageUrl,
            releaseDate = data.releaseDate,
            popularity = data.popularity,
            isLiked = data.isLiked
        )


    override fun toDataEntity(domain: Movie): FavoriteMovieEntity =
        FavoriteMovieEntity(
            id = domain.id,
            title = domain.title,
            genres = Json.encodeToString(
                domain.genres.map { genre -> GenreEntity(genre.name) }
            ),
            rating = domain.rating,
            imageUrl = domain.imageUrl,
            releaseDate = domain.releaseDate,
            popularity = domain.popularity,
            isLiked = domain.isLiked
        )
}