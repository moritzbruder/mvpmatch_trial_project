package de.moritzbruder.mvp_match_trial.data.model

import android.content.Context
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import de.moritzbruder.mvp_match_trial.R

@JsonIgnoreProperties(ignoreUnknown = true)
data class Movie (

    @JsonProperty("imdbID")
    val imdbId: String,

    @JsonProperty("Title")
    val title: String,

    @JsonProperty("Year")
    val year: String,

    @JsonProperty("Poster")
    val posterUrl: String,

    @JsonProperty("Runtime")
    val runtime: String?,

    @JsonProperty("Plot")
    val plotSummary: String,

    @JsonProperty("Ratings")
    val ratings: List<MovieRating>,

    @JsonProperty("Genre")
    private val genreString: String?,

    @JsonProperty("Awards")
    val awardsSummary: String?,

    @JsonProperty("Director")
    val directorName: String?

) {

    val genres: List<String> by lazy {
        genreString?.split(", ") ?: listOf()
    }

}

data class MovieRating (
    @JsonProperty("Source")
    val source: String,
    @JsonProperty("Value")
    val value: String
) {

    fun getDisplayText(context: Context): String {
        return context.getString(R.string.template_rating_summary, value, source)
    }

}