package jp.yuoku.github_reader.shared.data.remote.api.common

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher

expect class ApiClient() {
    val client: HttpClient
    val dispatcher: CoroutineDispatcher
}