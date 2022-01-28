package com.bosha.feature_main.ui.homelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.domain.view.viewcontroller.ViewController
import com.bosha.domain.view.viewcontroller.createScreen
import com.bosha.feature_main.databinding.FragmentHomeBinding
import com.bosha.feature_main.util.GridSpacingItemDecoration
import com.bosha.utils.extensions.applyInsetsFitsSystemWindows
import com.bosha.utils.extensions.setDecorFitsSystemWindows
import com.bosha.utils.extensions.setMarginTop
import com.bosha.utils.extensions.setPaddingTop
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.bosha.utils.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
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
            screen.views {
                progressBar.isVisible = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = screen {
        inflateView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initSearchTabNavigation()
        setUpRecycler()
        observeViewModel()

        screen.views {
            handleInset(fhRoot)
        }
    }

    private fun handleInset(view: View) {
        val initialRVInset = screen.binding.rvMovieList.paddingTop

        applyInsetsFitsSystemWindows(view) {
            val statusBarInset = it.getInsets(WindowInsetsCompat.Type.statusBars()).top
            screen.views {
                rvMovieList.setPaddingTop(initialRVInset + statusBarInset)
                tbSearch.setMarginTop(statusBarInset)
            }
            it
        }
    }

    private fun observeViewModel() = screen.viewModelInScope { viewModel ->
        viewModel.pagingFlow
            .onEach { rvAdapter.submitData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.sideEffectFlow
            .onEach(::handleSideEffect)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        rvAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source }
            .onEach(viewModel::handlePagingLoadState)
            .launchIn(viewLifecycleOwner.lifecycleScope)
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


