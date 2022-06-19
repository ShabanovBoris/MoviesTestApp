package com.bosha.feature_search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosha.core.DataStateDelegate
import com.bosha.core.EventEmitter
import com.bosha.core_domain.entities.Movie
import com.bosha.core_domain.interactors.SearchMoviesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesInteractor: SearchMoviesInteractor
) : ViewModel(), EventEmitter {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        logcat(LogPriority.ERROR) { throwable.localizedMessage!! }
        throwable.printStackTrace()
    }

    private val _dataFlow: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val dataFlow get() = _dataFlow.asStateFlow()

    private val _sideEffectFlow: MutableStateFlow<SideEffects?> = MutableStateFlow(null)
    val sideEffectFlow get() = _sideEffectFlow.asStateFlow()

    private var searchProcessJob: Job? = null

    /**
     * Search from local storage when remote returns error
     */
    fun searchMovies(title: String) {
        searchProcessJob?.cancel()
        val data = ThreadLocal<String>()
        searchProcessJob = viewModelScope.launch(handler + data.asContextElement(title)) {
            _sideEffectFlow.value = SideEffects.Loading

            combine(
                searchMoviesInteractor.searchByTitle(data.get().orEmpty()),
                searchMoviesInteractor.searchByTitleFromCache(data.get().orEmpty())
            ) { remote, local ->
                if (remote.isFailure) {
                    _sideEffectFlow.value = SideEffects.NetworkError(remote.exceptionOrNull())
                    local
                } else remote
            }.onEach {
                _dataFlow.value = it.getOrNull()
                _sideEffectFlow.value = SideEffects.Loaded
            }.collect()
        }
    }

    sealed interface SideEffects {
        object Loading : SideEffects
        object Loaded : SideEffects
        class NetworkError(val t: Throwable?) : SideEffects
    }
}