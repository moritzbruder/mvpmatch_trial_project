package de.moritzbruder.mvp_match_trial.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OmdbSearchResults (
    @JsonProperty("Search")
    val results: List<MovieSearchResult>?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MovieSearchResult (
    @JsonProperty("Title")
    val title: String,

    @JsonProperty("Year")
    val year: String,

    @JsonProperty("Poster")
    val posterUrl: String,

    @JsonProperty("imdbID")
    val imdbId: String
)