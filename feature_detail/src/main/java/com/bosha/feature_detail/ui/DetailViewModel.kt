package com.bosha.feature_detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bosha.domain.entities.MovieDetails
import com.bosha.domain.interactors.GetMoviesInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat

class DetailViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val getMoviesInteractor: GetMoviesInteractor
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, throwable ->
        logcat(LogPriority.ERROR) { throwable.localizedMessage!! }
        throwable.printStackTrace()
    }

    private val _dataFlow: MutableStateFlow<MovieDetails?> = MutableStateFlow(null)
    val dataFlow get() = _dataFlow.asStateFlow()

    private val _sideEffectFlow: MutableStateFlow<SideEffects> =
        MutableStateFlow(SideEffects.Loading)
    val sideEffectFlow get() = _sideEffectFlow.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(Id: String): DetailViewModel
    }

    init {
        load()
    }

    private fun load() = viewModelScope.launch(handler) {
        getMoviesInteractor.getDetails(id)
            .collect {
                if (it.isFailure)
                    _sideEffectFlow.value = SideEffects.NetworkError(it.exceptionOrNull())

                _dataFlow.value = it.getOrNull()

                _sideEffectFlow.value = SideEffects.Loaded
            }
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            id: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }

    sealed interface SideEffects {
        object Loading : SideEffects
        object Loaded : SideEffects
        class NetworkError(val t: Throwable?) : SideEffects
    }
}