package jp.yuoku.github_reader.android.ui.screen.user_search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import jp.yuoku.github_reader.android.R
import jp.yuoku.github_reader.android.ui.components.AppBar
import jp.yuoku.github_reader.android.ui.components.SearchBarUI
import jp.yuoku.github_reader.domain.model.entity.GitHubUser
import jp.yuoku.github_reader.feature.user_search.UserSearchAction
import jp.yuoku.github_reader.feature.user_search.UserSearchUiState
import jp.yuoku.github_reader.feature.user_search.UserSearchViewModel
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun UserSearchScreen(
    destinationsNavigator: DestinationsNavigator, viewModel: UserSearchViewModel = getViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        Log.d("debug", "LaunchedEffect")
    }
    val state by viewModel.state.collectAsState()
    UserSearchScreen(viewModel, state, destinationsNavigator)
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
)
private fun UserSearchScreen(
    viewModel: UserSearchViewModel,
    state: UserSearchUiState,
    destinationsNavigator: DestinationsNavigator
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppBar(titleRes = R.string.app_heading, actionIcons = {})
    }) { innerPadding ->

        SearchBarUI(
            placeholderText = stringResource(id = R.string.search_user),
            topPadding = innerPadding.calculateTopPadding(),
            onSearchClick = {
                viewModel.onIntent(UserSearchAction.OnSearchTextChanged(it))
                viewModel.onIntent(UserSearchAction.SearchUsers)
            },
            matchesFound = viewModel.users.value.isNotEmpty(),
            isLoading = state is UserSearchUiState.Loading
        ) {

            val listState = rememberLazyListState()
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = listState,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .consumedWindowInsets(innerPadding)
            ) {
                when (state) {
                    is UserSearchUiState.Error -> {
                        item {
                            Text(
                                text = state.errorMessage,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }

                    UserSearchUiState.Idle -> {
                    }

                    UserSearchUiState.Loading -> {
                        placeholder()
                    }

                    is UserSearchUiState.Success -> {
                        users(state.users.items, destinationsNavigator)
                    }
                }
            }
        }
    }
}

fun LazyListScope.users(
    users: List<GitHubUser>, destinationsNavigator: DestinationsNavigator
) {
    items(users.size) { item ->
        UsersCard(users[item], modifier = Modifier.clickable {
            // TODO: Impl screen transition
//            destinationsNavigator.navigate(DetailScreenDestination(item))
        })
    }
}

fun LazyListScope.placeholder() {
    item {
        CircularProgressIndicator()
    }
}

@Composable
fun UsersCard(
    user: GitHubUser,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row {
            AsyncImage(
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(80.dp),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).scale(Scale.FILL).data(user.avatarUrl).crossfade(true).build(),
                contentDescription = null,
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = user.id.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = user.login,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
