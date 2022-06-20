package de.moritzbruder.mvp_match_trial.view.search.result_list

import com.squareup.picasso.Picasso
import de.moritzbruder.mvp_match_trial.databinding.ListItemSecondarySearchResultBinding
import de.moritzbruder.mvp_match_trial.service.MovieSearchResult
import de.moritzbruder.mvp_match_trial.view.movie_detail.MovieDetailActivity

class SecondarySearchResultViewHolder(private val binding: ListItemSecondarySearchResultBinding): MovieSearchResultsRecyclerViewAdapter.MovieViewHolder(binding.root) {
    override fun presentMovie(searchResult: MovieSearchResult) {
        // Show Available info
        binding.textViewMovieTitle.text = searchResult.title

        // Load poster
        Picasso.get()
            .load(searchResult.posterUrl)
            .into(binding.imageViewMoviePoster)

        // Open detail activity on click
        binding.root.setOnClickListener {
            MovieDetailActivity.start(searchResult.imdbId, binding.root.context)
        }
    }
}