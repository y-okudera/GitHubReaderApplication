package jp.yuoku.github_reader.application.interactor

import jp.yuoku.github_reader.application.use_case.GitHubUserSearchUseCase
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class GitHubUserSearchInteractor(
    private val gitHubUserSearchRepository: GitHubUserSearchRepository
) : GitHubUserSearchUseCase, KoinComponent {

    override fun invoke(query: String, page: Int) = flow {

        val response =
            gitHubUserSearchRepository.searchGitHubUser(query, page)

        emit(response)

    }
}
