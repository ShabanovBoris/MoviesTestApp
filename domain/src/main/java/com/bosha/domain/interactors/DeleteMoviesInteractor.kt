package com.bosha.domain.interactors

import com.bosha.domain.repositories.MovieRepository

class DeleteMoviesInteractor(private val repository: MovieRepository) {

    suspend fun deleteFavorite(id: String) = repository.deleteFavorite(id)
}