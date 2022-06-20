package de.moritzbruder.mvp_match_trial.view.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import de.moritzbruder.mvp_match_trial.data.model.MovieFavorite
import de.moritzbruder.mvp_match_trial.data.persistence.AppDatabase
import de.moritzbruder.mvp_match_trial.data.persistence.getFavorites

class FavoriteMoviesActivityViewModel(application: Application): AndroidViewModel(application) {

    private val favoritesDao = AppDatabase.get(application).favorites()

    private val favorites = favoritesDao.getFavorites()

    fun getFavorites(): LiveData<List<MovieFavorite>> = favorites

}