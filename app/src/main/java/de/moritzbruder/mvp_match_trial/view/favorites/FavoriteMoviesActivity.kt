package de.moritzbruder.mvp_match_trial.view.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import de.moritzbruder.mvp_match_trial.R
import de.moritzbruder.mvp_match_trial.databinding.ActivityFavoriteMoviesBinding
import de.moritzbruder.mvp_match_trial.view.ConnectivityToast
import de.moritzbruder.mvp_match_trial.view.search.SearchActivity

class FavoriteMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteMoviesBinding
    private lateinit var viewModel: FavoriteMoviesActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.activity_title_favorites)

        viewModel = viewModels<FavoriteMoviesActivityViewModel>().value
        binding = ActivityFavoriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ConnectivityToast.register(this.application)

        val favoritesAdapter = MovieFavoritesRecyclerViewAdapter()
        binding.recyclerViewFavoriteMovies.apply {
            layoutManager = LinearLayoutManager(this@FavoriteMoviesActivity)
            adapter = favoritesAdapter
        }

        viewModel.getFavorites().observe(this) { favorites ->
            favoritesAdapter.items = favorites
            favoritesAdapter.notifyDataSetChanged()
        }

        binding.layoutButtonSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}