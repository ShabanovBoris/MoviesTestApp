package com.bosha.feature_main.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.core.navigation.navigate
import com.bosha.core.view.BaseFragment
import com.bosha.core.view.viewcontroller.Screen
import com.bosha.core_domain.entities.Movie
import com.bosha.feature_main.databinding.FragmentHomeBinding
import com.bosha.uikit.GridSpacingItemDecoration
import com.bosha.utils.extensions.applyInsetsFitsSystemWindows
import com.bosha.utils.extensions.setPaddingTop
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import logcat.LogPriority
import logcat.logcat

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentHomeBinding, FavoriteViewModel>() {

    override val screen = Screen<FragmentHomeBinding, FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = screen{
        onPreDraw {
           logcat(LogPriority.ERROR) { "FavoriteFragment: onCreateView onPreDraw" }
        }
        inflateView(inflater, container)
    }

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
        screen.viewModelInScope {
            it.dataFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .filterNotNull()
                .onEach(::setList)
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
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
        (rvMovieList.adapter as MovieListAdapter).submitList(list)
    }
}