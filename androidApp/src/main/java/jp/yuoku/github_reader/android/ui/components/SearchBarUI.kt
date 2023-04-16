package jp.yuoku.github_reader.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.yuoku.github_reader.android.R

@Composable
fun SearchBarUI(
    placeholderText: String = "",
    topPadding: Dp,
    onSearchClick: (String) -> Unit,
    matchesFound: Boolean,
    isLoading: Boolean,
    results: @Composable () -> Unit = {}
) {

    Column {

        Spacer(Modifier.size(topPadding))

        SearchBar(placeholderText = placeholderText, onSearchClick = onSearchClick)

        if (matchesFound) {
            Text("Results", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
            results()
        }
        else if (!isLoading) {
            NoSearchResults()
        }
    }
}

@Composable
private fun SearchBar(
    placeholderText: String,
    onSearchClick: (String) -> Unit
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val searchButtonEnabled = searchText.text.isNotEmpty()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchText,
            onValueChange = { newValue ->
                searchText = newValue
            },
            label = { Text(placeholderText, color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            singleLine = true,
            trailingIcon = {
                if (searchText.text.isNotEmpty()) {
                    IconButton(onClick = { searchText = TextFieldValue("") }) {
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "クリア",
                        )
                    }
                }
            },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = { onSearchClick(searchText.text) },
            enabled = searchButtonEnabled,
            modifier = Modifier.width(60.dp)
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "検索",
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun NoSearchResults() {

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(stringResource(id = R.string.no_search_results))
    }
}
