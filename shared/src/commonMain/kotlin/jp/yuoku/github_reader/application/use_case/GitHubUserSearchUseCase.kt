package jp.yuoku.github_reader.application.use_case

import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import kotlinx.coroutines.flow.Flow

interface GitHubUserSearchUseCase {
    operator fun invoke(query: String, page: Int): Flow<GitHubUserSearchResult>
}
