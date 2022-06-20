package de.moritzbruder.mvp_match_trial.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieFavorite (
    @PrimaryKey
    val imdbId: String,
    val type: MovieFavoriteType,
    val title: String,
    val posterUrl: String,
    val plotSummary: String,
    val ratings: List<MovieRating>
)

enum class MovieFavoriteType {
    favorite, exclude
}