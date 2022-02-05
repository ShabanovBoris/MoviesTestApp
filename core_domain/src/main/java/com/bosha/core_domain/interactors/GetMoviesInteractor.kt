package com.bosha.core_domain.interactors

import com.bosha.core_domain.repositories.MovieRepository

class GetMoviesInteractor(private val repository: MovieRepository) {

    fun fetchMovies() = repository.fetchMovies()

    fun getCachedMovies() = repository.getCachedMovies()

    fun getDetailsById(id: String) = repository.fetchMovieDetails(id)

    suspend fun getCachedMovieById(id: String) = repository.getCachedMovie(id)

    fun getFavoritesMovies() = repository.getFavoritesMovies()
}