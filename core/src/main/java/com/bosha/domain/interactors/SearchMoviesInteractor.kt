package com.bosha.domain.interactors

import com.bosha.domain.repositories.MovieRepository

class SearchMoviesInteractor(private val repository: MovieRepository)  {

    fun searchByTitle(title: String) = repository.searchByTitle(title)

    fun searchByTitleFromCache(title: String) = repository.searchByTitleFromCache(title)
}