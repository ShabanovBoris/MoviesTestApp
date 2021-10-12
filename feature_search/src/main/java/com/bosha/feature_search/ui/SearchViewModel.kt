package com.bosha.feature_search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosha.domain.entities.Movie
import com.bosha.domain.interactors.SearchMoviesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesInteractor: SearchMoviesInteractor
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        logcat(LogPriority.ERROR) { throwable.localizedMessage!! }
        throwable.printStackTrace()
    }

    private val _dataFlow: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val dataFlow get() = _dataFlow.asStateFlow()

    private val _sideEffectFlow: MutableStateFlow<SideEffects?> = MutableStateFlow(null)
    val sideEffectFlow get() = _sideEffectFlow.asStateFlow()

    /**
     * Search from local storage when remote returns error
     */
    fun searchMovies(title: String) = viewModelScope.launch(handler) {
        _sideEffectFlow.value = SideEffects.Loading

            combine(
                searchMoviesInteractor.searchByTitle(title),
                searchMoviesInteractor.searchByTitleFromCache(title)
            ) { remote, local ->
                if (remote.isFailure) {
                    _sideEffectFlow.value = SideEffects.NetworkError(remote.exceptionOrNull())
                    local
                } else remote
            }
            .onEach {
                _dataFlow.value = it.getOrNull()
                _sideEffectFlow.value = SideEffects.Loaded
            }
            .collect()
    }

    sealed interface SideEffects {
        object Loading : SideEffects
        object Loaded : SideEffects
        class NetworkError(val t: Throwable?) : SideEffects
    }
}