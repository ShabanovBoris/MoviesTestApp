package com.bosha.feature_search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.domain.entities.Movie
import com.bosha.feature_search.databinding.FragmentSearchBinding
import com.bosha.utils.extensions.onViewLifecycleWhenStarted
import com.bosha.utils.extensions.waitPreDraw
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.bosha.utils.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        waitPreDraw()

        setUpRecycler(view)
        initSearchListener()

        onViewLifecycleWhenStarted {
            viewModel.dataFlow
                .onEach(::setList)
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.sideEffectFlow
                .onEach(::handleSideEffect)
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun initSearchListener() {
        callbackFlow {
            binding.etSearching.doAfterTextChanged {
                trySend(it.toString())
            }
            awaitClose()
        }
            .debounce(400)
            .distinctUntilChanged()
            .onEach { viewModel.searchMovies(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpRecycler(view: View) {
        binding.rvSearchList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(view.context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, 30, true))
            adapter = MovieListAdapter {
                navigate {
                    target = NavCommand(Screens.DETAIL).setArgs(it.transitionName)
                    extras { addSharedElement(it, Screens.DETAIL.value) }
                }
            }
        }
    }

    private fun setList(list: List<Movie>?) {
        list ?: return
        (binding.rvSearchList.adapter as MovieListAdapter).submitList(list)
    }

    private fun handleSideEffect(effect: SearchViewModel.SideEffects?) {
        effect ?: return
        when (effect) {
            SearchViewModel.SideEffects.Loading -> binding.progressBar.isVisible = true
            SearchViewModel.SideEffects.Loaded -> binding.progressBar.isVisible = false
            is SearchViewModel.SideEffects.NetworkError -> showErrorToast(effect.t)
        }
    }

    private fun showErrorToast(t: Throwable?) {
        Toast.makeText(requireContext(), "${t?.message}", Toast.LENGTH_LONG).show()
    }
}