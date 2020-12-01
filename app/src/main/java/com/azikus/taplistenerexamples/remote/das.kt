package com.azikus.taplistenerexamples.remote

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import timber.log.Timber

private val okHttpKtor = HttpClient(CIO) {
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
    // Logging
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Timber.v(message)
            }
        }
        level = LogLevel.ALL
    }
    // Timeout
    install(HttpTimeout) {
        requestTimeoutMillis = 15000L
        connectTimeoutMillis = 15000L
        socketTimeoutMillis = 15000L
    }
    // Apply to All Requests
    defaultRequest {
        if (this.method != HttpMethod.Get) {
            contentType(ContentType.Application.Json)
        }
        accept(ContentType.Application.Json)
    }
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
}
