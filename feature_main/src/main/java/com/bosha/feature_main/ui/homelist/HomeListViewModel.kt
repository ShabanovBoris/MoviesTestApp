package com.bosha.feature_main.ui.homelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.bosha.core_domain.entities.Movie
import com.bosha.core_domain.interactors.AddMoviesInteractor
import com.bosha.core_domain.interactors.GetMoviesInteractor
import com.bosha.feature_main.data.repository.PagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val getMoviesInteractor: GetMoviesInteractor,
    private val addMoviesInteractor: AddMoviesInteractor,
    pagingRepository: PagingRepository
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

    val pagingFlow = pagingRepository.fetchMoviesPaging()
        .onEach {

                it.filter {
                    logcat(LogPriority.ERROR){ it.toString() }
                    true
                }

            _sideEffectFlow.value = SideEffects.Loaded
        }
        .cachedIn(viewModelScope)


    /**
     * todo delete old logic after add paging
     */
    init {
//        cacheLoad()
//        updateCache()

//        buildList {
//            add("a")
//            set(0 , "b")
//            reverse()
//        }.asFlow()
//            .collect()
    }

    /**
     * initial load does not trigger [source.append], that means we expect the [source.refresh]
     * only when the [source.append] is not [LoadState.Loading] and wise versa
     */
    fun handlePagingLoadState(loadState: CombinedLoadStates) {
        /** loader when loading new page */
        if (loadState.source.refresh !is LoadState.Loading)
            _sideEffectFlow.update {
                when (val state = loadState.source.append) {
                    LoadState.Loading -> SideEffects.Loading
                    is LoadState.NotLoading -> SideEffects.Loaded
                    is LoadState.Error -> SideEffects.NetworkError(state.error)
                }
            }
        /** loader when loading initial page */
        if (loadState.source.append !is LoadState.Loading)
            _sideEffectFlow.update {
                when (val state = loadState.source.refresh) {
                    LoadState.Loading -> SideEffects.Loading
                    is LoadState.NotLoading -> SideEffects.Loaded
                    is LoadState.Error -> SideEffects.NetworkError(state.error)
                }
            }
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
            .onEach { movie ->
                if (movie.isFailure)
                    _sideEffectFlow.update { SideEffects.NetworkError(movie.exceptionOrNull()) }
            }
            .onEach {
                it.getOrNull()?.let { list ->
                    addMoviesInteractor.insertMovies(list)
                }
                _sideEffectFlow.update { SideEffects.Loaded }
            }
            .collect()
    }

    sealed interface SideEffects {
        object Loading : SideEffects
        object Loaded : SideEffects
        class NetworkError(val t: Throwable?) : SideEffects
        object Empty : SideEffects
    }
}