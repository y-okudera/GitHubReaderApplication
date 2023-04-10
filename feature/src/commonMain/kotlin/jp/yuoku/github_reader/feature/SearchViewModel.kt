
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.yuoku.github_reader.application.use_case.GitHubUserSearchUseCase
import jp.yuoku.github_reader.domain.model.service.user_search.GitHubUserSearchResult
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
) : ViewModel(), KoinComponent, EventsDispatcherOwner<SearchViewModel.EventsListener> {

    val query: CMutableStateFlow<String> = MutableStateFlow("").cMutableStateFlow()

    private val gitHubUserSearchUseCase: GitHubUserSearchUseCase by inject()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val isLoading: CStateFlow<Boolean> = _isLoading.cStateFlow()

    val isButtonEnabled: CStateFlow<Boolean> =
        combine(isLoading, query) { isLoading, query ->
            isLoading.not() && query.isNotBlank()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false).cStateFlow()

    fun onSearchPressed() {
        val queryValue = query.value

        viewModelScope.launch {
            _isLoading.value = true

            try {
                val response = gitHubUserSearchUseCase.searchGitHubUser(queryValue, 1)
                eventsDispatcher.dispatchEvent { showSearchResult(response) }
            } catch (error: Throwable) {
                val errorDesc = error.message ?: error.toString()
                eventsDispatcher.dispatchEvent { showError(errorDesc) }
            } finally {
                _isLoading.value = false
            }
        }
    }

    interface EventsListener {
        fun showSearchResult(result: GitHubUserSearchResult)
        fun showError(error: String)
        fun routeToFooScreen()
    }
}
