package com.bosha.domain.interactors

import com.bosha.domain.repositories.MovieRepository

class GetMoviesInteractor(private val repository: MovieRepository) {

    fun fetchMovies() = repository.fetchMovies()

    fun getCachedMovies() = repository.getCachedMovies()

    fun getDetails(id: String) = repository.getMovieDetails(id)
}