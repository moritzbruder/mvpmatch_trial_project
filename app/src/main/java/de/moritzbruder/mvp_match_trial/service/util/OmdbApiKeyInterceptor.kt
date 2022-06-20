package de.moritzbruder.mvp_match_trial.service.util

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds a query paramter 'apikey' with the given [apiKey] value to each request.
 */
class OmdbApiKeyInterceptor (val apiKey: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest.url().newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()

        val newRequest = originalRequest.newBuilder().url(url).build()

        return chain.proceed(newRequest)

    }

}