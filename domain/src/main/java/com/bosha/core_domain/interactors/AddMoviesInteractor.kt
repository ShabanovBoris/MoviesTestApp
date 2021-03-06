package com.bosha.core_domain.interactors

import com.bosha.core_domain.entities.Movie
import com.bosha.core_domain.repositories.MovieRepository

class AddMoviesInteractor(private val repository: MovieRepository) {

    suspend fun insertMovies(list: List<Movie>){
        repository.insertCachedMovies(list)
    }

    suspend fun insertFavoriteMovie(movie: Movie){
        repository.insertFavoriteMovie(movie)
    }
}