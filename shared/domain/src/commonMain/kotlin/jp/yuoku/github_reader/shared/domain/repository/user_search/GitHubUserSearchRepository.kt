package jp.yuoku.github_reader.shared.domain.repository.user_search

import jp.yuoku.github_reader.shared.domain.model.service.user_search.GitHubUserSearchResult

interface GitHubUserSearchRepository {
    suspend fun searchGitHubUser(query: String, page: Int): GitHubUserSearchResult
}
