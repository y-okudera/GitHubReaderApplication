package jp.yuoku.github_reader.feature.user_search

sealed interface UserSearchAction {

    data class OnSearchTextChanged(val text: String) : UserSearchAction
    object ClearText : UserSearchAction
    object SearchUsers : UserSearchAction
}
