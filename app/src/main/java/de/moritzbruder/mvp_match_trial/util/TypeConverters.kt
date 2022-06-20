package de.moritzbruder.mvp_match_trial.util

import androidx.room.TypeConverter
import de.moritzbruder.mvp_match_trial.data.model.MovieRating

class MovieRatingListConverter {
    @TypeConverter
    fun storedStringToLanguages(value: String): List<MovieRating> {
        return value.split("\n").map { str ->
            val separated = str.split("|")
            MovieRating(separated[0], separated[1])
        }
    }
    @TypeConverter
    fun languagesToStoredString(ratingsList: List<MovieRating>): String {
        return ratingsList.joinToString("\n") { rating -> "${rating.source}|${rating.value}" }
    }
}
