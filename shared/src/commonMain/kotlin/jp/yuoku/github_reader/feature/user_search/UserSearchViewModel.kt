package jp.yuoku.github_reader.feature.user_search

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import jp.yuoku.github_reader.application.use_case.GitHubUserSearchUseCase
import jp.yuoku.github_reader.application.utility.result.asResult
import jp.yuoku.github_reader.application.utility.result.Result
import jp.yuoku.github_reader.domain.model.entity.GitHubUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserSearchViewModel : ViewModel(), KoinComponent {

    private val gitHubUserSearchUseCase: GitHubUserSearchUseCase by inject()

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    private val _users: MutableStateFlow<List<GitHubUser>> = MutableStateFlow(arrayListOf())
    var users = _users.asStateFlow()

    private val _state = MutableStateFlow<UserSearchUiState>(UserSearchUiState.Idle)
    var state = _state.asStateFlow()

    private var page: Int = 1
    fun onIntent(intent: UserSearchAction) {

        when (intent) {
            is UserSearchAction.OnSearchTextChanged -> {
                _searchText.update { intent.text }
            }
            is UserSearchAction.ClearText -> {
                _searchText.update { "" }
            }
            is UserSearchAction.SearchUsers -> {
                searchUsers()
            }
        }
    }

    private fun searchUsers() {

        viewModelScope.launch {

            if (_searchText.value.isEmpty()) {
                _state.update {
                    UserSearchUiState.Idle
                }
                return@launch
            }

            gitHubUserSearchUseCase.invoke(_searchText.value, page).asResult().collectLatest { result ->

                when (result) {
                    is Result.Error -> {
                        if (page == 1) {
                            _state.update {
                                UserSearchUiState.Error(result.exception.message)
                            }
                        }
                    }
                    Result.Idle -> {
                        if (page == 1) {
                            _state.update {
                                UserSearchUiState.Idle
                            }
                        }

                    }
                    Result.Loading -> {
                        if (page == 1) {
                            _state.update {
                                UserSearchUiState.Loading
                            }
                        }
                    }
                    is Result.Success -> {
                        if (page == 1) {
                            _state.update {
                                UserSearchUiState.Success(result.data)
                            }
                            _users.update {
                                result.data.items
                            }
                        } else {
                            _state.update {
                                (it as UserSearchUiState.Success).copy(
                                    users = GitHubUserSearchResult(
                                        it.users.items + result.data.items,
                                        result.data.totalCount,
                                        result.data.incompleteResults
                                    )
                                )
                            }

                            _users.update {
                                _users.value + result.data.items
                            }
                        }
                    }
                }

            }

        }

    }
}
