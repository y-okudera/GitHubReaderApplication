package jp.yuoku.github_reader.data.repository

import de.jensklingenberg.ktorfit.converter.builtin.CallResponseConverter
import de.jensklingenberg.ktorfit.converter.builtin.FlowResponseConverter
import de.jensklingenberg.ktorfit.ktorfit
import jp.yuoku.github_reader.data.remote.api.GitHubUserSearchApi
import jp.yuoku.github_reader.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import org.koin.core.component.KoinComponent

class GitHubUserSearchRepositoryImpl(
    private val apiClient : ApiClient
) : GitHubUserSearchRepository, KoinComponent {

    override suspend fun searchGitHubUser(query: String, page: Int): GitHubUserSearchResult {
        val ktorfit = ktorfit {
            baseUrl("https://api.github.com/")
            httpClient(apiClient.client)
            responseConverter(FlowResponseConverter(), CallResponseConverter())
        }
        val gitHubUserSearchApi = ktorfit.create<GitHubUserSearchApi>()

        return gitHubUserSearchApi.searchGitHubUser(query, page)
    }
}
