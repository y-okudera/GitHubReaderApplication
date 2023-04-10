package jp.yuoku.github_reader.data.remote.api

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult

interface GitHubUserSearchApi {
    @GET("search/users")
    suspend fun searchGitHubUser(@Query("q") query: String, @Query("page") page: Int): GitHubUserSearchResult
}
