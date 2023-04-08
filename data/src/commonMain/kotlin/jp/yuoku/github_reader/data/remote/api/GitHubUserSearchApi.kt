package jp.yuoku.github_reader.data.remote.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import jp.yuoku.github_reader.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import org.koin.core.component.KoinComponent

class GitHubUserSearchApi(
    private val apiClient : ApiClient
) : GitHubUserSearchRepository, KoinComponent {

    companion object {
        const val BASE_URL = "https://api.github.com/search/users"
    }

    override suspend fun searchGitHubUser(query: String, page: Int): GitHubUserSearchResult {
        val response = apiClient.client.get(BASE_URL) {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            parameter("q", query)
            parameter("page", page)
        }

        return if (response.status.isSuccess()) {
            response.body()
        } else {
            GitHubUserSearchResult(emptyList())
        }
    }
}
