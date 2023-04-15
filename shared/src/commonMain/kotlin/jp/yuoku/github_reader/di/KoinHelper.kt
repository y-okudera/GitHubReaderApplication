package jp.yuoku.github_reader.di

import jp.yuoku.github_reader.shared.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.shared.domain.repository.user_search.GitHubUserSearchRepository
import jp.yuoku.github_reader.shared.application.use_case.GitHubUserSearchUseCase
import jp.yuoku.github_reader.shared.feature.user_search.UserSearchViewModel
import org.koin.core.Koin
import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(sharedModule)
}

// Application
val Koin.gitHubUserSearchUseCase: GitHubUserSearchUseCase get() = get()

// Data
val Koin.apiClient: ApiClient get() = get()
val Koin.gitHubUserSearchRepository: GitHubUserSearchRepository get() = get()

// Feature
val Koin.userSearchViewModel: UserSearchViewModel get() = get()
