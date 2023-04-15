package jp.yuoku.github_reader.shared.feature.user_search

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.yuoku.github_reader.shared.domain.model.service.user_search.GitHubUserSearchResult
import jp.yuoku.github_reader.shared.application.use_case.GitHubUserSearchUseCase
import jp.yuoku.github_reader.shared.application.utility.result.asResult
import jp.yuoku.github_reader.shared.application.utility.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserSearchViewModel : ViewModel(), KoinComponent {

    private val gitHubUserSearchUseCase: GitHubUserSearchUseCase by inject()

    private val _state = MutableStateFlow<UserSearchState>(UserSearchState.Idle)
    var state = _state.asStateFlow()
    private var page: Int = 1
    fun onIntent(intent: UserSearchSideEffect) {

        when (intent) {
            is UserSearchSideEffect.SearchUsers -> {
                searchUsers()
            }
        }
    }

    private fun searchUsers() {

        viewModelScope.launch {

            gitHubUserSearchUseCase.invoke("kmm", page).asResult().collectLatest { result ->

                when (result) {
                    is Result.Error -> {
                        if (page == 1) {
                            _state.update {
                                UserSearchState.Error(result.exception.message)
                            }
                        }
                    }
                    Result.Idle -> {
                        if (page == 1) {
                            _state.update {
                                UserSearchState.Idle
                            }
                        }

                    }
                    Result.Loading -> {
                        if (page == 1) {
                            _state.update {
                                UserSearchState.Loading
                            }
                        }
                    }
                    is Result.Success -> {
                        if (page == 1) {
                            _state.update {
                                UserSearchState.Success(result.data)
                            }
                        } else {
                            _state.update {
                                (it as UserSearchState.Success).copy(
                                    users = GitHubUserSearchResult(
                                        it.users.items + result.data.items
                                    )
                                )
                            }
                        }
                    }
                }

            }

        }

    }
}
