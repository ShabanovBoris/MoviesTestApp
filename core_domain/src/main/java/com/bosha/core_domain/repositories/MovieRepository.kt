package com.bosha.core_domain.repositories

import com.bosha.core_domain.entities.Movie
import com.bosha.core_domain.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

typealias MoviesResult = Result<List<Movie>>

interface MovieRepository {
    /**
     * Remote
     */
    fun fetchMovies(): Flow<MoviesResult>

    fun fetchMovieDetails(id: String): Flow<Result<MovieDetails>>

    fun searchByTitle(title: String): Flow<MoviesResult>

    /**
     * Local
     */
    fun getCachedMovies(): Flow<MoviesResult>

    suspend fun insertCachedMovies(list: List<Movie>)

    suspend fun getCachedMovie(id: String): Movie

    fun searchByTitleFromCache(title: String): Flow<MoviesResult>

    fun getFavoritesMovies(): Flow<MoviesResult>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavorite(id: String)
}