package de.moritzbruder.mvp_match_trial.view.movie_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import de.moritzbruder.mvp_match_trial.R
import de.moritzbruder.mvp_match_trial.data.model.MovieFavoriteType
import de.moritzbruder.mvp_match_trial.databinding.ActivityMovieDetailBinding
import de.moritzbruder.mvp_match_trial.util.getNonNa

class MovieDetailActivity : AppCompatActivity() {

    companion object {

        const val extraKeyMovieImdbId = "MOVIE_IMDB_ID"

        fun start (movieId: String, context: Context) {
            context.startActivity(Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(extraKeyMovieImdbId, movieId)
            })
        }

    }

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.activity_title_movie_details)

        val movieId = intent.extras?.getString(extraKeyMovieImdbId) ?: let {
            Toast.makeText(this, getString(R.string.error_cannot_open_movie), Toast.LENGTH_SHORT).show()
            return finish()
        }

        viewModel = viewModels<MovieDetailActivityViewModel> { MovieDetailActivityViewModelFactory(this, movieId) }.value
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getMovie().observe(this) { movieResult ->
            binding.progressBarLoading.isGone = true
            movieResult.fold(onSuccess = { movie ->
                // Show movie details
                binding.textViewMovieTitle.text = movie.title
                binding.textViewMoviePlot.text = movie.plotSummary
                binding.textViewMovieYear.text = movie.year

                fillDetailSectionIfAvailable(movie.runtime, binding.textViewMovieRuntime, binding.layoutMovieRuntimeContainer)
                fillDetailSectionIfAvailable(movie.directorName, binding.textViewMovieDirector, binding.layoutMovieDirectorContainer)
                fillDetailSectionIfAvailable(movie.awardsSummary, binding.textViewMovieAwards, binding.layoutMovieAwardsContainer)

                binding.textViewMovieGenres.text = movie.genres.joinToString(" • ")

                // Load poster into image view
                Picasso.get().load(movie.posterUrl).into(binding.imageViewMoviePoster)

            }, onFailure = {
                binding.viewErrorIndicatorCover.isVisible = true
                binding.textViewError.isVisible = true
                listOf(
                    binding.textViewMovieTitle,
                    binding.textViewMoviePlot,
                    binding.textViewMovieYear
                ).forEach { it.isGone = true }
            })
        }

        viewModel.getFavorite().observe(this) { favorite ->
            // User decided to hide this, so we can exit the activity
            if (favorite?.type == MovieFavoriteType.exclude) {
                finish()
                return@observe
            }

            // Update favorite button to show the right state
            val imgRes = if (favorite?.type == MovieFavoriteType.favorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_empty
            binding.imageViewFavoriteButtonIcon.setImageResource(imgRes)
            binding.progressBarFavoriteButtonLoading.isGone = true
            binding.cardViewFavoriteButton.isVisible = true

        }

        binding.buttonChangeMovieFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        binding.buttonHideMovieInSearch.setOnClickListener {
            viewModel.hideFromSearch()
        }
    }

    private fun fillDetailSectionIfAvailable (text: String?, textView: TextView, layoutContainer: ViewGroup) {
        val nonNaText = text?.getNonNa()
        textView.text = nonNaText
        layoutContainer.isGone = nonNaText == null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

}