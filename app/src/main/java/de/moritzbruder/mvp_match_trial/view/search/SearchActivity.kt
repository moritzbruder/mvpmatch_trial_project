package de.moritzbruder.mvp_match_trial.view.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import de.moritzbruder.mvp_match_trial.databinding.ActivitySearchBinding
import de.moritzbruder.mvp_match_trial.view.search.result_list.MovieSearchResultsRecyclerViewAdapter

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = null
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewMovieList.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = MovieSearchResultsRecyclerViewAdapter()
        }

        val viewModel: SearchActivityViewModel by viewModels()

        binding.editTextSearch.addTextChangedListener {
            viewModel.search(it.toString())
        }

        binding.imageViewButtonClearSearch.setOnClickListener {
            binding.editTextSearch.text.clear()
            binding.editTextSearch.requestFocus()
        }

        binding.editTextSearch.requestFocus()

        binding.editTextSearch.addTextChangedListener { text ->
            binding.imageViewButtonClearSearch.isGone = (text?.length ?: 0) == 0
        }

        binding.buttonRetrySearch.setOnClickListener {
            viewModel.search(binding.editTextSearch.text.toString())
        }

        viewModel.filteredSearchResults.observe(this) { searchResults ->
            (binding.recyclerViewMovieList.adapter as? MovieSearchResultsRecyclerViewAdapter)?.let { adapter ->
                binding.layoutSearchErrorIndicator.isGone = searchResults.isSuccess
                if (searchResults.isSuccess) {
                    val resultList = searchResults.getOrThrow()
                    binding.layoutNoSearchResultsIndicator.isGone = resultList.isNotEmpty() || binding.editTextSearch.text.isBlank()
                    adapter.items = resultList
                    adapter.notifyDataSetChanged()
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}