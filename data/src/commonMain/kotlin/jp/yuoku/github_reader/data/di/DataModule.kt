package jp.yuoku.github_reader.data.di

import jp.yuoku.github_reader.data.remote.api.GitHubUserSearchApi
import jp.yuoku.github_reader.data.remote.api.common.ApiClient
import jp.yuoku.github_reader.domain.repository.user_search.GitHubUserSearchRepository
import org.koin.dsl.module

fun dataModule(baseUrl: String) = listOf(apiClient, gitHubUserSearchRepository(baseUrl))

val apiClient = module {
    single { ApiClient() }
}

fun gitHubUserSearchRepository(baseUrl: String) = module {
    single<GitHubUserSearchRepository> { GitHubUserSearchApi(get(), baseUrl) }
}
