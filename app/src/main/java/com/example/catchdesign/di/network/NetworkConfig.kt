package com.example.catchdesign.di.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClientProvider {
    val client = HttpClient(CIO) {

        defaultRequest {
            accept(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}