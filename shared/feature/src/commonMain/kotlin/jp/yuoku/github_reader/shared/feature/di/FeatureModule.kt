package jp.yuoku.github_reader.shared.feature.di

import jp.yuoku.github_reader.shared.feature.user_search.UserSearchViewModel
import org.koin.dsl.module

fun featureModule() = listOf(userSearchViewModel)

val userSearchViewModel = module {
    single { UserSearchViewModel() }
}
