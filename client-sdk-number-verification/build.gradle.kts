import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.vonage.numberverification"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        version = "1.1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        android.buildFeatures.buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String","VERSION_NAME","\"${defaultConfig.versionName}\"")
        }
        release {
            buildConfigField("String","VERSION_NAME","\"${defaultConfig.versionName}\"")
            isMinifyEnabled=  false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

publishing {
    publications {
        create<MavenPublication>("aar") {
            groupId = "com.vonage"
            artifactId = "client-sdk-number-verification"
            version = "1.1.1"
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
            pom {
                name = "Vonage Client SDK - Number Verification"
                description =
                    "Vonage Number Verification uses a mobile phone's Subscriber Identity Module (SIM) to prove a user's identity. This SDK enables making a HTTP request over cellular even when on WiFi. This SDK is designed to be used as part of a Number Verification flow."
                url = "https://github.com/Vonage/number-verification-sdk-android"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "vonage"
                        name = "Vonage"
                        email = "devrel@vonage.com"
                    }
                }
                scm {
                    connection = "scm:git:git:github.com/Vonage/number-verification-sdk-android.git"
                    developerConnection =
                        "scm:git:ssh://github.com/Vonage/number-verification-sdk-android.git"
                    url = "https://github.com/Vonage/number-verification-sdk-android"
                }

                pom.withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")
                    configurations.getByName("implementation").allDependencies.forEach {
                        val dependencyNode = dependenciesNode.appendNode("dependency")
                        dependencyNode.appendNode("groupId", it.group)
                        dependencyNode.appendNode("artifactId", it.name)
                        dependencyNode.appendNode("version", it.version)

                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = localProperties["nexusUsername"].toString()
                password = localProperties["nexusPassword"].toString()
            }
        }
    }
}

signing {
    sign(publishing.publications["aar"])
}

dependencies {
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
}