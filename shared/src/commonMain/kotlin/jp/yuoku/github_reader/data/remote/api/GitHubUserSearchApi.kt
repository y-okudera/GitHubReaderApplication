package jp.yuoku.github_reader.data.remote.api

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import jp.yuoku.github_reader.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import org.koin.core.component.KoinComponent

class GitHubUserSearchApi(
    private val apiClient : ApiClient,
    private val baseUrl : String
) : GitHubUserSearchRepository, KoinComponent {

    override suspend fun searchGitHubUser(
        query: String,
        page: Int
    ): GitHubUserSearchResult = apiClient.client.get("${baseUrl}search/users") {
        headers {
            append(HttpHeaders.ContentType, "application/json")
        }
        parameter("q", query)
        parameter("page", page)
    }.body()
}
