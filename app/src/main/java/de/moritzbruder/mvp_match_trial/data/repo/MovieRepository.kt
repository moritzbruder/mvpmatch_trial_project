package de.moritzbruder.mvp_match_trial.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import de.moritzbruder.mvp_match_trial.data.model.Movie
import de.moritzbruder.mvp_match_trial.service.Omdb
import kotlinx.coroutines.Dispatchers

object MovieRepository {

    private val movieMap = hashMapOf<String, Movie>()

    fun getMovie (id: String): LiveData<Result<Movie>> {
        val cached = movieMap[id]
        if (cached != null) {
            return liveData { emit(Result.success(cached)) }
        }
        return liveData (context = Dispatchers.IO) {
            try {
                val loaded = Omdb.service.getMovieDetails(id)
                movieMap[id] = loaded
                emit(Result.success(loaded))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

}