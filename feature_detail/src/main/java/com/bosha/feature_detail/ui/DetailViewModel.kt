package com.bosha.feature_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bosha.core.DataState
import com.bosha.core.TaskScheduler
import com.bosha.core.TypedState
import com.bosha.core.view.ScreenViewModel
import com.bosha.core.view.showError
import com.bosha.core_domain.entities.MovieDetails
import com.bosha.core_domain.interactors.AddMoviesInteractor
import com.bosha.core_domain.interactors.DeleteMoviesInteractor
import com.bosha.core_domain.interactors.GetMoviesInteractor
import com.bosha.core_domain.interactors.SearchMoviesInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import java.time.Duration
import javax.inject.Provider

class DetailViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val getMoviesInteractor: GetMoviesInteractor,
    private val addMoviesInteractor: AddMoviesInteractor,
    private val deleteMoviesInteractor: DeleteMoviesInteractor,
    private val searchMoviesInteractor: SearchMoviesInteractor,
    private val taskScheduler: Provider<TaskScheduler>
) : ScreenViewModel() {
    private var movieIsLiked = false

    val uiState = DataState<DetailsUISate>()

    init {
        load()
    }

    private fun load() = viewModelScope.launch() {
        combine(
            getMoviesInteractor.getDetailsById(id),
            getMoviesInteractor.getFavoritesMovies()
        ) { detail, favorites ->
            movieIsLiked =
                favorites.getOrNull()?.find { it.id.toString() == id }?.isLiked ?: false
            detail
        }
            .firstOrNull()
            ?.onFailure { error ->
                uiState.emit(TypedState.error(error))
                showError()
            }
            ?.onSuccess {
                uiState.emitData(DetailsUISate(it, movieIsLiked))
            }
    }

    fun changeFavoriteState(id: String, title: String, isLiked: Boolean) = viewModelScope.launch {
        if (!isLiked) {
            deleteMoviesInteractor.deleteFavorite(id)
            return@launch
        }
        runCatching {
            getMoviesInteractor.getCachedMovieById(id)
        }.onFailure {
            searchMoviesInteractor.searchByTitle(title).first {
                if (it.isSuccess) {
                    addMoviesInteractor.insertMovies(it.getOrThrow())
                    val movie = getMoviesInteractor.getCachedMovieById(id)
                    addMoviesInteractor.insertFavoriteMovie(movie.copy(isLiked = isLiked))
                }
                true
            }
        }
    }

    fun scheduleMovie(id: String, duration: Duration) {
        taskScheduler.get().scheduleNotification(id, duration)
    }

    @AssistedFactory
    interface Factory {
        fun create(Id: String): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            id: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }

    data class DetailsUISate(
        val movieDetails: MovieDetails,
        val isFavorite: Boolean
    ) {
        val genres get() = movieDetails.genres.joinToString(" ") { it.name }
    }
}