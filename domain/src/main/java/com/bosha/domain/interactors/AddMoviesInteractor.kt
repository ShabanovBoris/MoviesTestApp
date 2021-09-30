package com.bosha.domain.interactors

import com.bosha.domain.entities.Movie
import com.bosha.domain.repositories.MovieRepository

class AddMoviesInteractor(private val repository: MovieRepository) {

    suspend fun insertMovies(list: List<Movie>){
        repository.insertMovies(list)
    }
}