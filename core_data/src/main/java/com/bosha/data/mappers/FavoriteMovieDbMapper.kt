package com.bosha.data.mappers

import com.bosha.data.dto.local.FavoriteMovieEntity
import com.bosha.data.dto.local.GenreEntity
import com.bosha.domain.entities.Genre
import com.bosha.domain.entities.Movie
import com.bosha.domain.utils.Mapper
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