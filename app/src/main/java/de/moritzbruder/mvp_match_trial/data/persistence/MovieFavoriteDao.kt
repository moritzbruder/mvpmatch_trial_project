package de.moritzbruder.mvp_match_trial.data.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.moritzbruder.mvp_match_trial.data.model.Movie
import de.moritzbruder.mvp_match_trial.data.model.MovieFavorite
import de.moritzbruder.mvp_match_trial.data.model.MovieFavoriteType
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieFavoriteDao {

    @Query("SELECT * FROM moviefavorite")
    fun getAll(): LiveData<List<MovieFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: MovieFavorite)

    @Query("DELETE FROM moviefavorite WHERE imdbId = :favoriteImdbId")
    fun remove(favoriteImdbId: String)

    @Query("SELECT * FROM moviefavorite WHERE imdbId = :imdbId")
    fun getFavoriteForId(imdbId: String): LiveData<MovieFavorite?>

}

fun MovieFavoriteDao.setFavorite (movie: Movie, type: MovieFavoriteType) {
    val fav = MovieFavorite(movie.imdbId, type, movie.title, movie.posterUrl, movie.plotSummary, movie.ratings)
    this.insert(fav)
}

fun MovieFavoriteDao.getFavorites (): LiveData<List<MovieFavorite>> {
    return Transformations.map(getAll()) { list ->
        list.filter { it.type == MovieFavoriteType.favorite }
    }
}

fun MovieFavoriteDao.getExcludedIds (): LiveData<HashSet<String>> {
    return Transformations.map(getAll()) { list ->
        list
            .filter { it.type == MovieFavoriteType.exclude }
            .map { it.imdbId }
            .toHashSet()
    }
}