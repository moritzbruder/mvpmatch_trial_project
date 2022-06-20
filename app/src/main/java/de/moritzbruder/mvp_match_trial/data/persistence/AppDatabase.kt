package de.moritzbruder.mvp_match_trial.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.moritzbruder.mvp_match_trial.data.model.MovieFavorite
import de.moritzbruder.mvp_match_trial.util.MovieRatingListConverter

@Database(entities = [MovieFavorite::class], version = 1)
@TypeConverters(MovieRatingListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var instance: AppDatabase? = null

        fun get (context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "main-db"
                ).build()
            }
            return instance!!
        }

    }

    abstract fun favorites(): MovieFavoriteDao
}