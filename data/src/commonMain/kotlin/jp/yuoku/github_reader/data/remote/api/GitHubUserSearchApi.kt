package jp.yuoku.github_reader.data.remote.api

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import kotlinx.coroutines.flow.Flow

interface GitHubUserSearchApi {
    @GET("search/users")
    fun searchGitHubUser(@Query("q") query: String, @Query("page") page: Int): Flow<GitHubUserSearchResult>
}
