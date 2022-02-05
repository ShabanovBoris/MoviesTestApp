package com.bosha.feature_main.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosha.core_domain.entities.Movie
import com.bosha.core_domain.interactors.GetMoviesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getMoviesInteractor: GetMoviesInteractor
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        logcat(LogPriority.ERROR) { throwable.localizedMessage!! }
        throwable.printStackTrace()
    }

    private val _dataFlow: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val dataFlow get() = _dataFlow.asStateFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch(handler) {
        getMoviesInteractor.getFavoritesMovies()
            .onEach {
                _dataFlow.value = it.getOrNull()
                logcat(LogPriority.ERROR) { "get movies favorite $it" }
            }
            .collect()
    }
}