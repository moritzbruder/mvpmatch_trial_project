package de.moritzbruder.mvp_match_trial.service

import de.moritzbruder.mvp_match_trial.data.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {

    @GET("/")
    fun searchMovies (@Query("s") titleQuery: String): Call<OmdbSearchResults>

    @GET("/")
    suspend fun getMovieDetails (@Query("i") imdbId: String): Movie

}