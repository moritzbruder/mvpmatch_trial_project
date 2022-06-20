package de.moritzbruder.mvp_match_trial.view.movie_detail

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Room
import de.moritzbruder.mvp_match_trial.data.model.Movie
import de.moritzbruder.mvp_match_trial.data.model.MovieFavorite
import de.moritzbruder.mvp_match_trial.data.model.MovieFavoriteType
import de.moritzbruder.mvp_match_trial.data.persistence.AppDatabase
import de.moritzbruder.mvp_match_trial.data.persistence.setFavorite
import de.moritzbruder.mvp_match_trial.data.repo.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailActivityViewModel(application: Application, movieImdbId: String): AndroidViewModel(application) {

    private val favoritesDao = AppDatabase.get(application).favorites()

    private val movie = MovieRepository.getMovie(movieImdbId)
    private val movieFavorite = favoritesDao.getFavoriteForId(movieImdbId)

    fun getMovie(): LiveData<Result<Movie>> = movie

    fun getFavorite(): LiveData<MovieFavorite?> = movieFavorite

    fun toggleFavorite() {
        val movie = this.movie.value?.getOrNull() ?: return
        viewModelScope.launch(Dispatchers.IO) {
            if (movieFavorite.value?.type == MovieFavoriteType.favorite) {
                favoritesDao.remove(movie.imdbId)
            } else {
                favoritesDao.setFavorite(movie, MovieFavoriteType.favorite)
            }
        }
    }

    fun hideFromSearch () {
        val movie = this.movie.value?.getOrNull() ?: return
        viewModelScope.launch (Dispatchers.IO) {
            favoritesDao.setFavorite(movie, MovieFavoriteType.exclude)
        }
    }

}

class MovieDetailActivityViewModelFactory(private val activity: MovieDetailActivity, private val movieImdbId: String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailActivityViewModel(activity.application, movieImdbId) as T
    }
}