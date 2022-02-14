package com.bosha.feature_main.ui.favorite

import androidx.lifecycle.viewModelScope
import com.bosha.core.DataState
import com.bosha.core.view.BaseViewModel
import com.bosha.core_domain.entities.Movie
import com.bosha.core_domain.interactors.GetMoviesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getMoviesInteractor: GetMoviesInteractor
) : BaseViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        logcat(LogPriority.ERROR) { throwable.localizedMessage!! }
        throwable.printStackTrace()
    }

    val movies = DataState<List<Movie>?>()

    init {
        load()
    }

    private fun load() = viewModelScope.launch(handler) {
        getMoviesInteractor.getFavoritesMovies()
            .onEach {
                movies.emitData(it.getOrNull())
                logcat(LogPriority.ERROR) { "get movies favorite $it" }
            }
            .collect()
    }
}