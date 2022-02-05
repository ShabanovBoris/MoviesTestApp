package com.bosha.core_domain.interactors

import com.bosha.core_domain.repositories.MovieRepository

class DeleteMoviesInteractor(private val repository: MovieRepository) {

    suspend fun deleteFavorite(id: String) = repository.deleteFavorite(id)
}