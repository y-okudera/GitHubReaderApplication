package jp.yuoku.github_reader.application.interactor

import jp.yuoku.github_reader.application.use_case.GetMyPageUseCase
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import org.koin.core.component.KoinComponent

class GetMyPageInteractor(
    private val gitHubUserSearchRepository: GitHubUserSearchRepository
) : GetMyPageUseCase, KoinComponent {
    override suspend fun searchGitHubUser(query: String, page: Int): GitHubUserSearchResult {
        return gitHubUserSearchRepository.searchGitHubUser(query, page)
    }
}
