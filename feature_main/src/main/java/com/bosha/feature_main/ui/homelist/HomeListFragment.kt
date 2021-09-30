package com.bosha.feature_main.ui.homelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.domain.entities.Movie
import com.bosha.feature_main.R
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

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel by viewModels<HomeListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecycler(view)

        viewModel.dataFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(::setList)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.sideEffectFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(::handleSideEffect)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpRecycler(view: View) {
        binding.rvMovieList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(view.context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, 30, true))
            adapter = MovieListAdapter {
                navigate {
                    target = NavCommand(Screens.DETAIL).setArgs("$it")
                }
            }
        }
    }

    private fun setList(list: List<Movie>?) {
        list ?: return
        (binding.rvMovieList.adapter as MovieListAdapter).submitList(list)
    }

    private fun handleSideEffect(effect: HomeListViewModel.SideEffects){
        when(effect){
            HomeListViewModel.SideEffects.Loading -> binding.progressBar.isVisible = true
            HomeListViewModel.SideEffects.Loaded -> binding.progressBar.isVisible = false
            is HomeListViewModel.SideEffects.NetworkError -> showErrorToast(effect.t)
        }
    }

    private fun showErrorToast(t: Throwable?) {
        Toast.makeText(requireContext(),"${t?.message}", Toast.LENGTH_LONG).show()
    }
}


