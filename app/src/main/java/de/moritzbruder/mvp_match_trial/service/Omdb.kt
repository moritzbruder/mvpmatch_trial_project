package de.moritzbruder.mvp_match_trial.service

import de.moritzbruder.mvp_match_trial.service.util.OmdbApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val OMDB_API_KEY = "4fe41379"

object Omdb {

    private val baseUrl = "https://www.omdbapi.com"

    private val retrofitBuilder = let {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(OmdbApiKeyInterceptor(OMDB_API_KEY))
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val service = retrofitBuilder.create(OmdbService::class.java)

}