package jp.yuoku.github_reader.feature.user_search

sealed class UserSearchSideEffect {
    object SearchUsers : UserSearchSideEffect()
}
