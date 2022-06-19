package com.bosha.feature_main.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.lifecycle.asFlow
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.core.extensions.applyInsetsFitsSystemWindows
import com.bosha.core.extensions.setPaddingTop
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

@AndroidEntryPoint
class FavoriteFragment : ScreenFragment<FragmentHomeBinding, FavoriteViewModel>() {

    override val screen by screen<FragmentHomeBinding, FavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        screen.views {
            tbSearch.isGone = true
            applyInsetsFitsSystemWindows(this.root) {
                rvMovieList.setPaddingTop(
                    it.getInsets(WindowInsetsCompat.Type.statusBars()).top
                )
                it
            }
        }
        observeInScope(viewModel.movies, onReady = ::setList)
    }

    private fun setUpRecycler() = screen.views {
        rvMovieList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(root.context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, 30, true))
            adapter = MovieListAdapter {
                navigate {
                    target = NavCommand(Screens.DETAIL).setArgs(it.transitionName)
                    extras { addSharedElement(it, Screens.DETAIL.value) }
                }
            }
        }
    }

    private fun setList(list: List<Movie>?) = screen.views {
        list ?: return@views
        (rvMovieList.adapter as MovieListAdapter).submitList(list)
    }
}