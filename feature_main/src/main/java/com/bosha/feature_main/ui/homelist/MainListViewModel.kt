package com.bosha.feature_main.ui.homelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosha.domain.interactors.GetMoviesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class MainListViewModel @Inject constructor(
    private val getMoviesInteractor: GetMoviesInteractor
) : ViewModel() {

    init {
        viewModelScope.launch {
            getMoviesInteractor.fetchMovies()
                .collect {
                    it.getOrNull()?.forEach {
                        logcat(LogPriority.ERROR) { it.toString() }
                    }
                }
        }
    }
}