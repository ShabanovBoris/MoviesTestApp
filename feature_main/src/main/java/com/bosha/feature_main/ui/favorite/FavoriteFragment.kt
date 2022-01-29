package com.bosha.feature_main.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bosha.domain.entities.Movie
import com.bosha.domain.view.viewcontroller.ViewController
import com.bosha.domain.view.viewcontroller.createScreen
import com.bosha.feature_main.custom.GridSpacingItemDecoration
import com.bosha.feature_main.databinding.FragmentHomeBinding
import com.bosha.utils.extensions.applyInsetsFitsSystemWindows
import com.bosha.utils.extensions.setPaddingTop
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.bosha.utils.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val screen: ViewController<FragmentHomeBinding, FavoriteViewModel> = createScreen()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = screen{
        onPreDraw {
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
        inflateView(inflater, container)
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