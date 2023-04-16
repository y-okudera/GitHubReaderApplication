package jp.yuoku.github_reader.domain.repository.user_search

import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult

interface GitHubUserSearchRepository {
    suspend fun searchGitHubUser(query: String, page: Int): GitHubUserSearchResult
}
