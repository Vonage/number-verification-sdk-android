package com.vonage.numberverification.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vonage.numberverification.VGNumberVerificationClient
import com.vonage.numberverification.VGNumberVerificationParameters
import com.vonage.numberverification.test.ui.theme.NumberVerificationTheme

class MainActivity : ComponentActivity() {

    private val myServerURL = "https://MYSERVER.com" //URL to your server running the Server SDK
    private val headers = mapOf("x-my-header" to "My Value") //Headers to be sent with the request (useful if your server requires authentication)
    private val queryParameters = mapOf("query-param" to "value") //Query parameters to be sent with the request.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberVerificationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        VGNumberVerificationClient.initializeSdk(this.applicationContext)

        val params = VGNumberVerificationParameters(
            url = myServerURL,
            headers = headers,
            queryParameters = queryParameters
        )

        val response = VGNumberVerificationClient.getInstance().startNumberVerification(params, true)
        if (response.optString("error") != "") {
            // error
        } else {
            val status = response.optInt("http_status")
            if (status == 200) {
                // 200 OK
            } else {
                // error
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NumberVerificationTheme {
        Greeting("Android")
    }
}