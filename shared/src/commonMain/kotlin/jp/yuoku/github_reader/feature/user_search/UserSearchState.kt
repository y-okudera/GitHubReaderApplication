package jp.yuoku.github_reader.feature.user_search

import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult

sealed interface UserSearchState {

    object Loading: UserSearchState

    object Idle : UserSearchState

    data class Success(val users: GitHubUserSearchResult) : UserSearchState

    data class Error(val errorMessage: String) : UserSearchState

}
