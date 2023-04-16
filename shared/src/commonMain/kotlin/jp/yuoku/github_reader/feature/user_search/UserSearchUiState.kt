package jp.yuoku.github_reader.feature.user_search

import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult

sealed interface UserSearchUiState {

    object Loading: UserSearchUiState

    object Idle : UserSearchUiState

    data class Success(val users: GitHubUserSearchResult) : UserSearchUiState

    data class Error(val errorMessage: String) : UserSearchUiState

}
