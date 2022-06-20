package de.moritzbruder.mvp_match_trial.view.search

import android.app.Application
import androidx.lifecycle.*
import de.moritzbruder.mvp_match_trial.data.model.Movie
import de.moritzbruder.mvp_match_trial.data.persistence.AppDatabase
import de.moritzbruder.mvp_match_trial.data.persistence.getExcludedIds
import de.moritzbruder.mvp_match_trial.data.persistence.getFavorites
import de.moritzbruder.mvp_match_trial.service.MovieSearchResult
import de.moritzbruder.mvp_match_trial.service.Omdb
import de.moritzbruder.mvp_match_trial.service.OmdbSearchResults
import de.moritzbruder.mvp_match_trial.util.zip
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivityViewModel(application: Application): AndroidViewModel(application) {

    private val favoritesDao = AppDatabase.get(application).favorites()
    private val excludedMoviesIds = favoritesDao.getExcludedIds()

    private val searchResults = MutableLiveData<Result<List<MovieSearchResult>>>(Result.success(listOf()))
    private var searchRequest: Call<OmdbSearchResults>? = null

    val filteredSearchResults = searchResults.zip(excludedMoviesIds) { result, excluded ->
        if (result?.isSuccess == true) {
            val results = result.getOrThrow()
            val filteredResults = results.filter { excluded?.contains(it.imdbId)?.not() ?: true }
            return@zip Result.success(filteredResults)
        } else {
            return@zip result ?: Result.success(listOf())
        }
    }

    fun search (rawTerm: String) {
        // Trim search term
        val term = rawTerm.trim()

        // Cancel any previous requests
        searchRequest?.cancel()

        // Set to empty list if search is empty
        if (term.isEmpty()) {
            searchResults.value = Result.success(listOf())
            return
        }

        // Start new search request w. server
        searchRequest = Omdb.service.searchMovies(term).also { request -> request.enqueue(object: Callback<OmdbSearchResults> {
            override fun onResponse(call: Call<OmdbSearchResults>, response: Response<OmdbSearchResults>) {
                // We have a response (which may be empty)
                if (response.isSuccessful) {
                    val results = response.body()!!.results
                    searchResults.value = Result.success(results ?: listOf())
                    return
                } else {
                    // Show error message
                    searchResults.value = Result.failure(NetworkException(response.errorBody()))
                }
            }
            override fun onFailure(call: Call<OmdbSearchResults>, t: Throwable) {
                if (!call.isCanceled) {
                    searchResults.value = Result.failure(t)
                }
            }
        }) }
    }

}

class NetworkException(errorBody: ResponseBody?): Exception(errorBody?.string() ?: "Unknown exception")