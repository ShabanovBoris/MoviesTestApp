package com.bosha.moviesdemo.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {



    private companion object {
        const val KEY_ONE_TIME = "keyOneTimeShow"
    }
}