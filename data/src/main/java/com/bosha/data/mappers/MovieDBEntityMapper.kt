package com.bosha.data.mappers

import com.bosha.data.dto.local.FavoriteMovieEntity
import com.bosha.data.dto.local.GenreEntity
import com.bosha.data.dto.local.MovieEntity
import com.bosha.domain.entities.Genre
import com.bosha.domain.entities.Movie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MovieDBEntityMapper @Inject constructor() {
    fun toMovieEntity(item: Movie): MovieEntity {
        return item.let {
            MovieEntity(
                id = it.id,
                title = it.title,
                genres = Json.encodeToString(
                    it.genres.map { genre -> GenreEntity(genre.name) }
                ),
                rating = it.rating,
                imageUrl = it.imageUrl,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                isLiked = it.isLiked
            )
        }
    }

    fun toMovieEntityList(list: List<Movie>): List<MovieEntity> {
        return list.map {
            toMovieEntity(it)
        }
    }


    fun toMovie(item: MovieEntity): Movie {
        return item.let {
            Movie(
                id = it.id,
                title = it.title,
                genres = (Json.decodeFromString(it.genres) as List<GenreEntity>)
                    .map { genre -> Genre(genre.name) },
                rating = it.rating,
                imageUrl = it.imageUrl,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                isLiked = it.isLiked
            )
        }
    }

    fun toMovieList(list: List<MovieEntity>): List<Movie> {
        return list.map {
            toMovie(it)
        }
    }

    fun toMovie(item: FavoriteMovieEntity): Movie {
        return item.let {
            Movie(
                id = it.id,
                title = it.title,
                genres = (Json.decodeFromString(it.genres) as List<GenreEntity>)
                    .map { genre -> Genre(genre.name) },
                rating = it.rating,
                imageUrl = it.imageUrl,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                isLiked = it.isLiked
            )
        }
    }

    fun toFavoriteMovie(item: Movie): FavoriteMovieEntity {
        return item.let {
            FavoriteMovieEntity(
                id = it.id,
                title = it.title,
                genres = Json.encodeToString(
                    it.genres.map { genre -> GenreEntity(genre.name) }
                ),
                rating = it.rating,
                imageUrl = it.imageUrl,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                isLiked = it.isLiked
            )
        }
    }
}