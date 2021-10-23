package com.bosha.data.mappers

import com.bosha.data.datasource.remote.impl.MoviesRemoteDataSource
import com.bosha.data.dto.remote.JsonMovie
import com.bosha.domain.entities.Genre
import com.bosha.domain.entities.Movie
import com.bosha.domain.utils.Mapper
import javax.inject.Inject

class MovieNetworkMapper @Inject constructor(): Mapper<JsonMovie, Movie> {

    var genresMap: Map<Int, Genre>? = null

    override fun toDomainEntity(data: JsonMovie): Movie = Movie(
        id = data.id,
        title = data.title,
        genres = findGenres(data.genreIds, requireNotNull(genresMap)),
        reviewCount = data.voteCount,
        rating = (data.voteAverage) / 2,
        imageUrl = MoviesRemoteDataSource.baseImagePosterUrl + data.posterPath,
        detailImageUrl = MoviesRemoteDataSource.baseImageBackdropUrl + data.backdropPath,
        storyLine = data.overview,
        releaseDate = data.releaseDate ?: "No release Date",
        popularity = data.popularity
    )

    override fun toDataEntity(domain: Movie): JsonMovie {
        throw NotImplementedError()
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