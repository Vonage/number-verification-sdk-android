package com.vonage.numberverification

import android.content.Context
import android.net.Uri
import android.util.Log
import com.vonage.numberverification.network.CellularNetworkManager
import com.vonage.numberverification.network.NetworkManager
import org.json.JSONObject
import java.net.URL

class VGNumberVerificationClient private constructor(networkManager: CellularNetworkManager) {
    private val networkManager: NetworkManager = networkManager

    /* This method performs a GET request given a URL with cellular connectivity
    - Parameters:
      - params: Parameters to configure your GET request
      - debug: A flag to include or not the url trace in the response
    */
    fun startNumberVerification(params: VGNumberVerificationParameters, debug: Boolean): JSONObject {
        val uri = constructURL(params)
        val networkManager: NetworkManager = getCellularNetworkManager()
        return networkManager.openWithDataCellular( uri, params.headers, debug)
    }

    private fun getCellularNetworkManager(): NetworkManager {
        return networkManager
    }

    private fun constructURL(params: VGNumberVerificationParameters): URL {
        val uriBuilder = Uri.parse(params.url).buildUpon()
        for ((key, value) in params.queryParameters) {
            uriBuilder.appendQueryParameter(key, value)
        }
        val uri = uriBuilder.build().toString()
        return URL(uri)
    }

    companion object {
        private var instance: VGNumberVerificationClient? = null
        private var currentContext: Context? = null

        @Synchronized
        fun initializeSdk(context: Context): VGNumberVerificationClient {
            var currentInstance = instance
            if (null == currentInstance || currentContext != context) {
                val nm = CellularNetworkManager(context)
                currentContext = context
                currentInstance = VGNumberVerificationClient(nm)
            }
            instance = currentInstance
            return currentInstance
        }

        @Synchronized
        fun getInstance(): VGNumberVerificationClient {
            val currentInstance = instance
            checkNotNull(currentInstance) {
                VGNumberVerificationClient::class.java.simpleName +
                        " is not initialized, call initializeSdk(...) first"
            }
            return currentInstance
        }
    }
}