package de.moritzbruder.mvp_match_trial.view.search.result_list

import androidx.lifecycle.LifecycleOwner
import com.squareup.picasso.Picasso
import de.moritzbruder.mvp_match_trial.data.repo.MovieRepository
import de.moritzbruder.mvp_match_trial.databinding.ListItemPrimarySearchResultBinding
import de.moritzbruder.mvp_match_trial.service.MovieSearchResult
import de.moritzbruder.mvp_match_trial.view.movie_detail.MovieDetailActivity

class PrimarySearchResultViewHolder(private val binding: ListItemPrimarySearchResultBinding): MovieSearchResultsRecyclerViewAdapter.MovieViewHolder(binding.root) {
    override fun presentMovie(searchResult: MovieSearchResult) {
        // Show available data
        binding.textViewMovieTitle.text = searchResult.title

        // Load poster image
        Picasso.get()
            .load(searchResult.posterUrl)
            .into(binding.imageViewMoviePoster)

        // Load movie details
        (binding.root.context as? LifecycleOwner)?.let { lifecycleOwner ->
            MovieRepository.getMovie(searchResult.imdbId).observe(lifecycleOwner) { movieResult ->
                movieResult.fold(onSuccess = { movie ->
                    binding.textViewMoviePlot.text = movie.plotSummary
                }, onFailure = {
                    // todo error state
                })
            }
        }

        // Open detail activity on click
        binding.root.setOnClickListener {
            MovieDetailActivity.start(searchResult.imdbId, binding.root.context)
        }
    }
}