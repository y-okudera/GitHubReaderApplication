package jp.yuoku.github_reader.di

import jp.yuoku.github_reader.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import jp.yuoku.github_reader.application.use_case.GitHubUserSearchUseCase
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
