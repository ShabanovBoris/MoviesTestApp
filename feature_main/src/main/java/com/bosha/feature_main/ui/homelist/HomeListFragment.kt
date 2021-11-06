package com.bosha.feature_main.ui.homelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.domain.view.ViewController
import com.bosha.domain.view.createScreen
import com.bosha.feature_main.databinding.FragmentHomeBinding
import com.bosha.feature_main.util.GridSpacingItemDecoration
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.bosha.utils.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeListFragment : Fragment() {

    private val screen: ViewController<FragmentHomeBinding, HomeListViewModel> = createScreen()

    private val rvAdapter by lazy {
        MovieListPagingAdapter {
            navigate {
                target = NavCommand(Screens.DETAIL).setArgs(it.transitionName)
                extras { addSharedElement(it, Screens.DETAIL.value) }
            }
            screen.view {
                progressBar.isVisible = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = screen {

        afterViewInflated {
            observeViewModel()
            setUpRecycler()
            initSearchNavigation()
        }

        inflateView(inflater, container)
    }

    private fun observeViewModel() = screen.viewModelInScope { viewModel ->
        viewModel.pagingFlow
                .onEach(rvAdapter::submitData)
                .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.sideEffectFlow
                .onEach(::handleSideEffect)
                .launchIn(viewLifecycleOwner.lifecycleScope)

            rvAdapter.loadStateFlow
                .onEach(::pagingLoadStateHandler)
                .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initSearchNavigation() = screen.view{
        tbSearch.setOnClickListener {
            navigate {
                target = NavCommand(Screens.SEARCH)
                options {}
            }
        }
    }

    private fun setUpRecycler() = screen.view {
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

    private fun handleSideEffect(effect: HomeListViewModel.SideEffects) = screen.view {
        when (effect) {
            HomeListViewModel.SideEffects.Loading -> progressBar.isVisible = true
            HomeListViewModel.SideEffects.Loaded -> progressBar.isVisible = false
            is HomeListViewModel.SideEffects.NetworkError -> showErrorToast(effect.t)
        }
    }

    private fun pagingLoadStateHandler(loadState: CombinedLoadStates) = screen.view {
        when (val state = loadState.source.append) {
            is LoadState.NotLoading -> progressBar.isVisible = false
            LoadState.Loading -> progressBar.isVisible = true
            is LoadState.Error -> showErrorToast(state.error)
        }
        when (val state = loadState.source.refresh) {
            is LoadState.NotLoading -> {
            }
            LoadState.Loading -> progressBar.isVisible = true
            is LoadState.Error -> showErrorToast(state.error)
        }
    }

    private fun showErrorToast(t: Throwable?) {
        Toast.makeText(requireContext(), "${t?.message}", Toast.LENGTH_LONG).show()
    }
}


