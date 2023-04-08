package jp.yuoku.github_reader.application.use_case

import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult

interface GetMyPageUseCase {
    suspend fun searchGitHubUser(query: String, page: Int): GitHubUserSearchResult
}
