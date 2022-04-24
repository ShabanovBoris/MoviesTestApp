package com.bosha.core_data.mappers

import com.bosha.core.Mapper
import com.bosha.core_data.datasource.remote.impl.MoviesRemoteDataSource
import com.bosha.core_data.dto.remote.JsonMovie
import com.bosha.core_domain.entities.Genre
import com.bosha.core_domain.entities.Movie
import javax.inject.Inject

class MovieNetworkMapper @Inject constructor(): Mapper<JsonMovie, Movie> {

    var genresMap: Map<Int, Genre>? = null

    override fun toDomainEntity(data: JsonMovie, page: Int?): Movie = Movie(
        id = data.id,
        title = data.title,
        genres = findGenres(data.genreIds, requireNotNull(genresMap)),
        reviewCount = data.voteCount,
        rating = (data.voteAverage) / 2,
        imageUrl = MoviesRemoteDataSource.baseImagePosterUrl + data.posterPath,
        detailImageUrl = MoviesRemoteDataSource.baseImageBackdropUrl + data.backdropPath,
        storyLine = data.overview,
        releaseDate = data.releaseDate ?: "No release Date",
        popularity = data.popularity,
        page = page
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