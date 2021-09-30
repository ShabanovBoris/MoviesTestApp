package com.bosha.feature_main.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.domain.entities.Movie
import com.bosha.feature_main.R
import com.bosha.feature_main.databinding.FragmentHomeBinding
import com.bosha.feature_main.ui.homelist.MovieListAdapter
import com.bosha.feature_main.util.GridSpacingItemDecoration
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.bosha.utils.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoriteFragment : Fragment()  {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel by viewModels<FavoriteViewModel>()

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
                    options {  }
                }
            }
        }
    }

    private fun setList(list: List<Movie>?) {
        list ?: return
        (binding.rvMovieList.adapter as MovieListAdapter).submitList(list)
    }
}