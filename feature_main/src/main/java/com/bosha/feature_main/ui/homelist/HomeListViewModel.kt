package com.bosha.feature_main.ui.homelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosha.domain.entities.Movie
import com.bosha.domain.interactors.AddMoviesInteractor
import com.bosha.domain.interactors.GetMoviesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val getMoviesInteractor: GetMoviesInteractor,
    private val addMoviesInteractor: AddMoviesInteractor
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        logcat(LogPriority.ERROR) { throwable.localizedMessage!! }
        throwable.printStackTrace()
    }

    private val _dataFlow: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val dataFlow get() = _dataFlow.asStateFlow()

    private val _sideEffectFlow: MutableStateFlow<SideEffects> =
        MutableStateFlow(SideEffects.Loading)
    val sideEffectFlow get() = _sideEffectFlow.asStateFlow()

    init {
        cacheLoad()
        updateCache()
    }

    private fun cacheLoad() = viewModelScope.launch(handler) {
        getMoviesInteractor.getCachedMovies()
            .onEach {
                _dataFlow.value = it.getOrNull()
            }
            .collect()
    }

    private fun updateCache() = viewModelScope.launch(handler) {
        getMoviesInteractor.fetchMovies()
            .onEach {
                if (it.isFailure)
                    _sideEffectFlow.value = SideEffects.NetworkError(it.exceptionOrNull())
            }
            .onEach {
                it.getOrNull()?.let { list ->
                    addMoviesInteractor.insertMovies(list)
                }
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