package jp.yuoku.github_reader.application.interactor

import jp.yuoku.github_reader.application.use_case.GitHubUserSearchUseCase
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import org.koin.core.component.KoinComponent

class GitHubUserSearchInteractor(
    private val gitHubUserSearchRepository: GitHubUserSearchRepository
) : GitHubUserSearchUseCase, KoinComponent {
    override suspend fun searchGitHubUser(query: String, page: Int): GitHubUserSearchResult {
        return gitHubUserSearchRepository.searchGitHubUser(query, page)
    }
}
