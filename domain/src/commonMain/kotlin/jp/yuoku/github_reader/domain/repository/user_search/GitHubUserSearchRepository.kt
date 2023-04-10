package jp.yuoku.github_reader.domain.repository.user_search

import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import kotlinx.coroutines.flow.Flow

interface GitHubUserSearchRepository {
    fun searchGitHubUser(query: String, page: Int): Flow<GitHubUserSearchResult>
}
