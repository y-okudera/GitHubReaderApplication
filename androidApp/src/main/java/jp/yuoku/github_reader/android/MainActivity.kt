package jp.yuoku.github_reader.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.yuoku.github_reader.shared.application.use_case.GitHubUserSearchUseCase

import jp.yuoku.github_reader.di.sharedModule
import jp.yuoku.github_reader.shared.domain.model.entity.GitHubUser
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Koin を初期化
        startKoin {
            androidContext(this@MainActivity)
            modules(sharedModule)
        }

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

class SearchViewModel : KoinComponent {
    val gitHubUserSearchUseCase: GitHubUserSearchUseCase by inject()
}

@Composable
fun SearchScreen(viewModel: SearchViewModel = SearchViewModel()) {
    val gitHubUserSearchUseCase = viewModel.gitHubUserSearchUseCase
    val query = remember { mutableStateOf("") }
    val users = remember { mutableStateListOf<GitHubUser>() }
    val searchJob = remember { mutableStateOf<Job?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = query.value,
            onValueChange = { newQuery ->
                query.value = newQuery
                searchJob.value?.cancel()
                searchJob.value = viewModelScope.launch {
                    users.clear()
                    users.addAll(gitHubUserSearchUseCase.searchGitHubUser(query.value, page = 1).items)
                }
            },
            label = { Text("GitHub User Search") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            items(users) { user ->
                UserItem(user)
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = rememberImagePainter(user.avatar_url),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Text(
            text = user.login,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MyApp {
        SearchScreen(api = GitHubApi())
    }
}
