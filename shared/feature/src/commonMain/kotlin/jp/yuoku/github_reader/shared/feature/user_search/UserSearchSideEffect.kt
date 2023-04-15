package jp.yuoku.github_reader.shared.feature.user_search

sealed class UserSearchSideEffect {
    object SearchUsers : UserSearchSideEffect()
}
