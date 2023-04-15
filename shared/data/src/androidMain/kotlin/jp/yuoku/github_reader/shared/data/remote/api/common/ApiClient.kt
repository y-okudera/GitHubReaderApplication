package jp.yuoku.github_reader.shared.data.remote.api.common

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

actual class ApiClient actual constructor() {
    actual val client: HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = false
            })
        }
    }

    actual val dispatcher: CoroutineDispatcher = Dispatchers.Default
}