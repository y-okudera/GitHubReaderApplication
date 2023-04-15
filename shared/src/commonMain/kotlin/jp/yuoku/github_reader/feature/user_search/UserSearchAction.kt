package jp.yuoku.github_reader.feature.user_search

sealed interface UserSearchAction {
    data class SearchUsers(val query: String) : UserSearchAction
}
