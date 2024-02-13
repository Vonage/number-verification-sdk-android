package com.vonage.numberverification.network

import org.json.JSONObject
import java.net.URL

internal interface NetworkManager {
    fun openWithDataCellular(url: URL, headers: Map<String, String>?, debug: Boolean): JSONObject
    fun postWithDataCellular(url: URL, headers: Map<String, String>, body: String?): JSONObject
}