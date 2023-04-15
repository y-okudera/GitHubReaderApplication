package jp.yuoku.github_reader.di

import jp.yuoku.github_reader.application.interactor.GitHubUserSearchInteractor
import jp.yuoku.github_reader.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import jp.yuoku.github_reader.application.use_case.GitHubUserSearchUseCase
import jp.yuoku.github_reader.data.remote.api.GitHubUserSearchApi
import jp.yuoku.github_reader.feature.user_search.UserSearchViewModel
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() = startKoin {
    modules(
        listOf(
            module {
                single { ApiClient() }
            },
            module {
                single<GitHubUserSearchRepository> {
                    GitHubUserSearchApi(get(), "https://api.github.com/")
                }
            },
            module {
                single<GitHubUserSearchUseCase> { GitHubUserSearchInteractor(get()) }
            },
            module {
                single<UserSearchViewModel> { UserSearchViewModel() }
            },
        )
    )
}

val Koin.apiClient: ApiClient get() = get()
val Koin.gitHubUserSearchRepository: GitHubUserSearchRepository get() = get()
val Koin.gitHubUserSearchUseCase: GitHubUserSearchUseCase get() = get()
val Koin.userSearchViewModel: UserSearchViewModel get() = get()
