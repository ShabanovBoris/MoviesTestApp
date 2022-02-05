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
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.bosha.core.navigation.navigate
import com.bosha.core.observe
import com.bosha.core_domain.entities.Actor
import com.bosha.core_domain.entities.MovieDetails
import com.bosha.feature_detail.R
import com.bosha.feature_detail.databinding.ActorItemBinding
import com.bosha.feature_detail.databinding.FragmentDetailBinding
import com.bosha.feature_detail.utils.datetime.schedule
import com.bosha.uikit.SimpleAdapter
import com.bosha.uikit.SimpleRvAdapter
import com.bosha.utils.extensions.applyInsetsFitsSystemWindows
import com.bosha.utils.extensions.doOnEndTransition
import com.bosha.utils.extensions.setPaddingTop
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
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
        NotificationManagerCompat.from(requireContext()).cancelAll()
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        applyInsetsFitsSystemWindows(view) {
            binding.detailContainer.setPaddingTop(
                it.getInsets(WindowInsetsCompat.Type.statusBars()).top
            )
            it
        }
        observe(
            viewModel.uiState,
            onReady = ::setUpView,
            onLoading = {
                binding.pbDetailsProgress.isVisible = true
            },
            onError = ::showErrorToast
        )
    }


    override fun onResume() {
        super.onResume()
        /**
         * if [setUpTransitionAnim] did not call callback from [setSharedElementEnterTransition],
         * ie when fragment opened from deeplink from outside
         * animate manually
         */
        getAnimGroup().forEach {
            it.animate().alpha(1F).setDuration(500).start()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpActorsRecycler() {
        binding.rvActors.apply {
            setHasFixedSize(true)
            adapter = SimpleAdapter<ActorItemBinding, Actor> { binding, item ->
                binding.tvActorFullname.text = item.name

                binding.ivAvatar.load(item.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_round_person)
                    error(R.drawable.ic_round_person)
                }
            }
        }
    }

    private fun showErrorToast(t: Throwable?) {
        Toast.makeText(requireContext(), "${t?.message}", Toast.LENGTH_LONG).show()
    }

    private fun setUpView(uiState: DetailViewModel.DetailsUISate) = binding.apply {
        waitImageLoading(uiState.movieDetails.imageBackdrop)
        setUpActorsRecycler()
        setUpListeners(uiState.movieDetails)

        (binding.rvActors.adapter as SimpleRvAdapter<ActorItemBinding, Actor>).items =
            uiState.movieDetails.actors
        tvMainTitle.text = uiState.movieDetails.title
        tvGenres.text = uiState.genres
        rbRating.rating = uiState.movieDetails.votes.toFloat()
        tvRating.text = uiState.movieDetails.votes.toString()
        tvStory.text = uiState.movieDetails.overview
        tvRunningTime.text = getString(R.string.runtime, uiState.movieDetails.runtime)
        acbFavorite.checked = uiState.isFavorite

        binding.pbDetailsProgress.isVisible = false
    }

    private fun setUpListeners(details: MovieDetails) {
        binding.ibSchedule.setOnClickListener {
            schedule { duration ->
                viewModel.scheduleMovie(details.id.toString(), duration)
            }
        }
        binding.acbFavorite.onCheckChange { isChecked ->
            viewModel.changeFavoriteState(details.id.toString(), details.title, isChecked)
        }
        binding.tvWebView.setOnClickListener {
            navigate {
                target = NavCommand(Screens.WEB_VIEW).setArgs(details.id.toString())
            }
        }
    }

    private fun waitImageLoading(dataUrl: String) = binding.apply {
        val request = ImageRequest.Builder(requireContext())
            .data(dataUrl)
            .target {
                ivMainImage.setImageDrawable(it)
                setBWFilter(ivMainImage)
                //resume
                startPostponedEnterTransition()
            }
            .build()
        requireContext().imageLoader.enqueue(request)
    }

    // set black-white filter
    private fun setBWFilter(image: ImageView) {
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
            acbFavorite,
            gradient,
            tvGenres,
            ibSchedule,
            tvSchedule
        )
    }
}





