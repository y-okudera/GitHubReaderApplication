package jp.yuoku.github_reader.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import jp.yuoku.github_reader.android.ui.screen.user_search.NavGraphs
import jp.yuoku.github_reader.android.ui.theme.GitHubReaderAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GitHubReaderAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }

    }

}
