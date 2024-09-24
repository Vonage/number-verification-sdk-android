# com.vonage.client-sdk-number-verification

[Vonage Number Verification](https://developer.vonage.com/en/number-verification/overview) uses a mobile phone's Subscriber Identity Module (SIM) to prove a user's identity. This SDK enables making a HTTP request over cellular even when on WiFi. This SDK is designed to be used as part of a Number Verification flow, please read the [guide](https://developer.vonage.com/en/getting-started-network/authentication) for more information.

## Installation

build.gradle -> dependencies add

```
implementation 'com.vonage:client-sdk-number-verification:1.1.1'
```

## Usage

```kotlin
    VGNumberVerificationClient.initializeSdk(this.applicationContext)

    val params = VGNumberVerificationParameters(
        url = "http://www.vonage.com",
        headers = mapOf("x-my-header" to "My Value") ,
        queryParameters = mapOf("query-param" to "value"),
        maxRedirectCount = 10
    )

    val response = VGNumberVerificationClient.getInstance().startNumberVerification(params, true)
    if (response.optString("error") != "") {
        // error
    } else {
        val status = response.optInt("http_status")
        val jsonReponse = response.getJSONObject("response_body") // Body of response parsed to JSON (NULL if not JSON)
        val rawReponse = response.optString("response_raw_body") // RAW string of response body (Only populated if not JSON)
        if (status == 200) {
            // 200 OK
        } else {
            // error
        }
    }
```
