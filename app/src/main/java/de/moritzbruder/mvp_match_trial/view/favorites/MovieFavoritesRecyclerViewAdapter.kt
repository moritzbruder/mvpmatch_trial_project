package de.moritzbruder.mvp_match_trial.view.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.moritzbruder.mvp_match_trial.data.model.MovieFavorite
import de.moritzbruder.mvp_match_trial.databinding.ListItemMovieFavoriteBinding
import de.moritzbruder.mvp_match_trial.view.movie_detail.MovieDetailActivity

class MovieFavoritesRecyclerViewAdapter(): RecyclerView.Adapter<MovieFavoritesRecyclerViewAdapter.MovieFavoriteViewHolder>() {

    var items: List<MovieFavorite> = listOf()

    inner class MovieFavoriteViewHolder(private val binding: ListItemMovieFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun present (movieFavorite: MovieFavorite) {
            binding.textViewMovieTitle.text = movieFavorite.title
            binding.textViewMoviePlot.text = movieFavorite.plotSummary
            binding.textViewMovieRatings.text = movieFavorite.ratings.randomOrNull()?.getDisplayText(binding.root.context)
            Picasso.get().load(movieFavorite.posterUrl).into(binding.imageViewPoster)
            binding.root.setOnClickListener {
                MovieDetailActivity.start(movieFavorite.imdbId, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteViewHolder {
        val binding = ListItemMovieFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieFavoriteViewHolder, position: Int) {
        holder.present(items[position])
    }

    override fun getItemCount(): Int = items.size


}