package de.moritzbruder.mvp_match_trial.util

fun String.getNonNa(): String? {
    return if (this.lowercase() == "n/a") null else this
}