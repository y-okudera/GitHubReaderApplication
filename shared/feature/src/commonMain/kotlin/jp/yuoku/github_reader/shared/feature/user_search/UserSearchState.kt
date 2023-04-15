package jp.yuoku.github_reader.shared.feature.user_search

import jp.yuoku.github_reader.shared.domain.model.service.user_search.GitHubUserSearchResult

sealed interface UserSearchState {

    object Loading: UserSearchState

    object Idle : UserSearchState

    data class Success(val users: GitHubUserSearchResult) : UserSearchState

    data class Error(val errorMessage: String) : UserSearchState

}
