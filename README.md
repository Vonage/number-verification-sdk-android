# com.vonage.client-sdk-number-verification

[Vonage Number Verification]() uses a mobile phone's Subscriber Identity Module (SIM) to prove a user's identity. This SDK enables making a HTTP request over cellular even when on WiFi. This SDK is designed to be used as part of a Number Verification flow, please read the [guide]() for more information.

## Installation

build.gradle -> dependencies add

```
implementation 'com.vonage:client-sdk-number-verification:1.0.0'
```

## Usage

```kotlin
    VGNumberVerificationClient.initializeSdk(this.applicationContext)

    val params = VGNumberVerificationParameters(
        url = "http://www.vonage.com",
        headers = mapOf("x-my-header" to "My Value") ,
        queryParameters = mapOf("query-param" to "value")
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
```