package jp.yuoku.github_reader.shared.data.di

import jp.yuoku.github_reader.shared.data.remote.api.GitHubUserSearchApi
import jp.yuoku.github_reader.shared.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.shared.domain.repository.user_search.GitHubUserSearchRepository
import org.koin.dsl.module

fun dataModule(baseUrl: String) = listOf(apiClient, gitHubUserSearchRepository(baseUrl))

val apiClient = module {
    single { ApiClient() }
}

fun gitHubUserSearchRepository(baseUrl: String) = module {
    single<GitHubUserSearchRepository> { GitHubUserSearchApi(get(), baseUrl) }
}
