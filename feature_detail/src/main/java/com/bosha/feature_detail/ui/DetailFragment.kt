package com.bosha.feature_detail.ui

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.request.ImageRequest
import com.bosha.domain.entities.MovieDetails
import com.bosha.feature_detail.R
import com.bosha.feature_detail.databinding.FragmentDetailBinding
import com.bosha.feature_detail.utils.datetime.schedule
import com.bosha.utils.extensions.doOnEndTransition
import com.bosha.utils.navigation.Screens
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val movieId: String by lazy {
        requireNotNull(requireArguments().getString("id"))
    }

    @Inject
    lateinit var viewModelAssistedFactory: DetailViewModel.Factory

    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.provideFactory(viewModelAssistedFactory, movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailBinding.inflate(inflater, container, false).also {
        _binding = it
        setUpTransitionAnim()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpActorsRecycler()

        viewModel.dataFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(::setUpView)
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

    private fun setUpActorsRecycler() {
        binding.rvActors.apply {
            setHasFixedSize(true)
            adapter = ActorRecyclerAdapter()
        }
    }

    private fun handleSideEffect(effect: DetailViewModel.SideEffects) {
        when (effect) {
            DetailViewModel.SideEffects.Loading -> binding.pbDetailsProgress.isVisible = true
            DetailViewModel.SideEffects.Loaded -> binding.pbDetailsProgress.isVisible = false
            is DetailViewModel.SideEffects.NetworkError -> showErrorToast(effect.t)
        }
    }

    private fun showErrorToast(t: Throwable?) {
        Toast.makeText(requireContext(), "${t?.message}", Toast.LENGTH_LONG).show()
    }

    private fun setUpView(details: MovieDetails?) = binding.apply {
        details ?: return@apply

        waitImageLoading(details.imageBackdrop)

        (binding.rvActors.adapter as ActorRecyclerAdapter).list = details.actors

        tvMainTitle.text = details.title

        /** genres*/
        for (g in details.genres) {
            tvGenres.append(g.name + " ")
        }

        rbRating.rating = details.votes.toFloat()
        tvRating.text = details.votes.toString()

        tvStory.text = details.overview

        ibSchedule.setOnClickListener {
            schedule { duration ->
                viewModel.scheduleMovie(details.id.toString(), duration)
            }
        }

        tvRunningTime.text = "${details.runtime} min"

        ibFavorite.isChecked = viewModel.movieIsLiked
        ibFavorite.setOnCheckedChangeListener { _, boolean ->
            viewModel.addDeleteFavorite(details.id.toString(), details.title, boolean)
        }
    }

    private fun waitImageLoading(dataUrl: String) = binding.apply {
        val request = ImageRequest.Builder(requireContext())
            .data(dataUrl)
            .target {
                ivMainImage.setImageDrawable(it)
                setFilter(ivMainImage)
                //resume
                startPostponedEnterTransition()
            }
            .build()
        requireContext().imageLoader.enqueue(request)
    }

    // set black-white filter
    private fun setFilter(image: ImageView) {
        val matrix = ColorMatrix().apply {
            set(
                floatArrayOf(
                    0.33f, 0.33f, 0.33f, 0f, 0f,
                    0.33f, 0.33f, 0.33f, 0f, 0f,
                    0.33f, 0.33f, 0.33f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }
        image.colorFilter = ColorMatrixColorFilter(matrix)
    }

    private fun setUpTransitionAnim() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 500
            endContainerColor = TypedValue().also {
                requireContext().theme.resolveAttribute(
                    R.attr.colorPrimaryVariant,
                    it, true
                )
            }.data
            doOnEndTransition {
                getAnimGroup().forEach {
                    it.animate().alpha(1F).setDuration(500).start()
                }
            }
        }
        getAnimGroup().forEach { it.alpha = 0f }
        binding.ivMainImage.transitionName = Screens.DETAIL.value
        //wait
        postponeEnterTransition()
    }

    private fun getAnimGroup() = binding.run {
        listOf(
            tvMainTitle,
            rbRating,
            tvRating,
            tvRunningTime,
            tvRunningTime,
            ibFavorite,
            gradient,
            tvGenres,
            ibSchedule,
            tvSchedule
        )
    }
}





