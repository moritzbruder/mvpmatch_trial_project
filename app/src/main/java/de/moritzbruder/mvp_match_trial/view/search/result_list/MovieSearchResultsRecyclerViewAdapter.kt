package de.moritzbruder.mvp_match_trial.view.search.result_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.moritzbruder.mvp_match_trial.databinding.ListItemPrimarySearchResultBinding
import de.moritzbruder.mvp_match_trial.databinding.ListItemSecondarySearchResultBinding
import de.moritzbruder.mvp_match_trial.service.MovieSearchResult

private const val viewTypePrimaryResult = 0
private const val viewTypeSecondaryResult = 1

class MovieSearchResultsRecyclerViewAdapter: RecyclerView.Adapter<MovieSearchResultsRecyclerViewAdapter.MovieViewHolder>() {

    var items = listOf<MovieSearchResult>()

    abstract class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        abstract fun presentMovie(searchResult: MovieSearchResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return if (viewTypePrimaryResult == viewType) {
            val binding = ListItemPrimarySearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PrimarySearchResultViewHolder(binding)
        } else {
            val binding = ListItemSecondarySearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SecondarySearchResultViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.presentMovie(movie)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            viewTypePrimaryResult
        } else {
            viewTypeSecondaryResult
        }
    }

    override fun getItemCount(): Int = items.size
}

