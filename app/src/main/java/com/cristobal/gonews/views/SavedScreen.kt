package com.cristobal.gonews.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cristobal.gonews.utils.Utils
import com.cristobal.gonews.viewmodels.SavedScreenVM

@Composable
fun SavedScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedScreenVM = viewModel()
) {
    val articlesList by viewModel.savedArticles.collectAsState()
    BuildView(
        modifier = modifier,
        articlesList = articlesList,
    )
}

@Composable
private fun BuildView(
    modifier: Modifier = Modifier,
    articlesList: List<com.cristobal.gonews.api_services.models.Article> = emptyList()
) {
    Box(
        modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .padding(10.dp)
    ) {
        LazyColumn {
            items(articlesList, key = { it.id }) { article ->
                var isOpenCard by rememberSaveable { androidx.compose.runtime.mutableStateOf(false) }
                if (isOpenCard) {
                    NewsDetailCard(
                        imageUrl = article.urlToImage ?: "",
                        title = article.title,
                        author = article.author ?: "Unknown author",
                        description = article.description ?: "No description available",
                        link = article.url,
                        publishedAt = Utils.extractDateFromTimestamp(
                            article.publishedAt
                        ),
                        source = article.source?.name ?: "Unknown source",
                        subtitle = article.description ?: "No description available",
                        onShrinkClick = { isOpenCard = false }
                    )
                } else {
                    NewsListItem(
                        imageUrl = article.urlToImage ?: "",
                        imageDesc = article.title,
                        topic = article.title,
                        publishedAt = Utils.extractDateFromTimestamp(
                            article.publishedAt
                        ),
                        onExpandClick = { isOpenCard = true }
                    )
                }
                HorizontalDivider(
                    Modifier
                        .fillMaxWidth()
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                )
            }

        }
    }
}

@Preview
@Composable
fun SavedScreenPreview() {
    BuildView(

    )
}