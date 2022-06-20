package de.moritzbruder.mvp_match_trial.view

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.widget.Toast
import de.moritzbruder.mvp_match_trial.R

object ConnectivityToast {

    fun register (application: Application) {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, object :
            ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                super.onLost(network)
                Toast.makeText(application, application.getString(R.string.error_connection_lost), Toast.LENGTH_SHORT).show()
            }
        })

    }

}