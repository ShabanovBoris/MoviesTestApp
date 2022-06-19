package com.bosha.feature_main.ui.homelist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.core.DataStateDelegate
import com.bosha.core.extensions.*
import com.bosha.core.navigation.NavCommand
import com.bosha.core.navigation.Screens
import com.bosha.core.navigation.navigate
import com.bosha.core.observeInScope
import com.bosha.core.view.ScreenFragment
import com.bosha.core.view.viewcontroller.screen
import com.bosha.core_domain.entities.Movie
import com.bosha.feature_main.databinding.FragmentHomeBinding
import com.bosha.uikit.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy

@AndroidEntryPoint
class HomeListFragment : ScreenFragment<FragmentHomeBinding, HomeListViewModel>() {

    override val screen by screen<FragmentHomeBinding, HomeListViewModel>()

    private val rvAdapter by lazy {
        MovieListPagingAdapter {
            navigate {
                target = NavCommand(Screens.DETAIL).setArgs(it.transitionName)
                extras { addSharedElement(it, Screens.DETAIL.value) }
            }
            binding.progressBar.isVisible = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchTabNavigation()
        setUpRecycler()
        observeViewModel()
        applyStatusBarInsetsToViews(binding.fhRoot)
    }

    private fun applyStatusBarInsetsToViews(view: View) {
        val initialRVInset = binding.rvMovieList.paddingTop
        applyInsetsFitsSystemWindows(view) {
            val statusBarInset = it.getInsets(WindowInsetsCompat.Type.statusBars()).top
            screen.views {
                rvMovieList.setPaddingTop(initialRVInset + statusBarInset)
                tbSearch.setMarginTop(statusBarInset)
            }
            it
        }
    }

    private fun observeViewModel() {
        launch {
            viewModel.pagingFlow.collect(rvAdapter::submitData)
        }
        launch {
            viewModel.sideEffectFlow.collect(::handleSideEffect)
        }
        launch {
            rvAdapter.loadStateFlow
                .distinctUntilChangedBy { it.source }
                .collect(viewModel::handlePagingLoadState)
        }

    }

    private fun initSearchTabNavigation() = screen.views {
        tbSearch.setOnClickListener {
            setDecorFitsSystemWindows(true)
            navigate {
                target = NavCommand(Screens.SEARCH)
                options {}
            }
        }
    }

    private fun setUpRecycler() = screen.views {
        rvMovieList.apply {
            setHasFixedSize(true)
            overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS
            layoutManager = GridLayoutManager(requireContext(), 2).apply {
                initialPrefetchItemCount = 15
            }
            addItemDecoration(GridSpacingItemDecoration(2, 30, true))
            adapter = rvAdapter.withLoadStateFooter(MoviesLoadStateAdapter { rvAdapter.retry() })
        }
    }

    private fun handleSideEffect(effect: HomeListViewModel.SideEffects) = screen.views {
        when (effect) {
            HomeListViewModel.SideEffects.Loading -> {
                progressBar.isVisible = true
            }
            HomeListViewModel.SideEffects.Loaded -> progressBar.isVisible = false
            is HomeListViewModel.SideEffects.NetworkError -> showErrorToast(effect.t)
            else -> {}
        }
    }

    private fun showErrorToast(t: Throwable?) {
        Toast.makeText(requireContext(), "${t?.message}", Toast.LENGTH_LONG).show()
    }
}


