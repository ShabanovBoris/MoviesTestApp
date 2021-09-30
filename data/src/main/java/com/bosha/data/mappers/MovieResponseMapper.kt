package com.bosha.data.mappers

import com.bosha.data.dto.remote.JsonMovie
import com.bosha.data.datasource.remote.impl.MoviesRemoteDataSource.Companion.baseImageBackdropUrl
import com.bosha.data.datasource.remote.impl.MoviesRemoteDataSource.Companion.baseImagePosterUrl
import com.bosha.domain.entities.Genre
import com.bosha.domain.entities.Movie
import javax.inject.Inject

class MovieResponseMapper @Inject constructor() {
    fun toMovieList(jsonMovie: List<JsonMovie>, genres: Map<Int, Genre>): List<Movie> {
        return jsonMovie.map {
            Movie(
                id = it.id,
                title = it.title,
                genres = findGenres(it.genreIds, genres),
                reviewCount = it.voteCount,
                rating = (it.voteAverage) / 2,
                imageUrl = baseImagePosterUrl + it.posterPath,
                detailImageUrl = baseImageBackdropUrl + it.backdropPath,
                storyLine = it.overview,
                releaseDate = it.releaseDate ?: "No release Date",
                popularity = it.popularity
            )
        }
    }

    private fun findGenres(genreIds: List<Int>, genres: Map<Int, Genre>): List<Genre> {
        val listOfGenres = mutableListOf<Genre>()
        genreIds.forEach {
            genres[it]?.let { genre ->
                listOfGenres.add(genre)
            }
        }
        return listOfGenres.toList()
    }
}