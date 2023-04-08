package jp.yuoku.github_reader.application.di

import jp.yuoku.github_reader.application.interactor.GitHubUserSearchInteractor
import jp.yuoku.github_reader.application.use_case.GitHubUserSearchUseCase
import org.koin.dsl.module

fun applicationModule() = listOf(gitHubUserSearchUseCase)

val gitHubUserSearchUseCase = module {
    single<GitHubUserSearchUseCase> { GitHubUserSearchInteractor(get()) }
}