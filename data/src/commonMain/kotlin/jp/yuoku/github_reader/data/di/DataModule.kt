package jp.yuoku.github_reader.data.di

import jp.yuoku.github_reader.data.remote.api.GitHubUserSearchApi
import jp.yuoku.github_reader.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import org.koin.dsl.module

fun dataModule() = listOf(apiClient, gitHubUserSearchRepository)

val apiClient = module {
    single { ApiClient() }
}

val gitHubUserSearchRepository = module {
    single<GitHubUserSearchRepository> { GitHubUserSearchApi(get()) }
}
