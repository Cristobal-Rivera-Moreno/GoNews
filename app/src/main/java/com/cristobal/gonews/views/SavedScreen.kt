package com.cristobal.gonews.views

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cristobal.gonews.api_services.models.Article
import com.cristobal.gonews.utils.Utils
import com.cristobal.gonews.viewmodels.SavedScreenVM
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SavedScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedScreenVM = viewModel()
) {
    val articlesList by viewModel.savedArticles.collectAsState()
    BuildView(
        modifier = modifier,
        articlesList = articlesList,
        deleteArticleHandler = viewModel::removeArticle
    )
}

@Composable
private fun BuildView(
    modifier: Modifier = Modifier,
    articlesList: List<com.cristobal.gonews.api_services.models.Article> = emptyList(),
    deleteArticleHandler: (Article) -> Unit = {},
) {
    val context = LocalContext.current
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

                val offsetX = remember { Animatable(0f) }
                val scope = rememberCoroutineScope()

                // Threshold to trigger the "save" action (e.g., swipe 150dp left)
                val threshold = 300f

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onDragEnd = {
                                    scope.launch {
                                        // 2. Check if swipe distance met the threshold
                                        if (offsetX.value > threshold) {

                                            Toast.makeText(
                                                context, "Article removed from saved: ${article.title}", Toast.LENGTH_SHORT
                                            ).show()
                                            deleteArticleHandler(article)
                                        }
                                        // 3. Animate back to the starting position
                                        offsetX.animateTo(
                                            0f,
                                            spring(stiffness = Spring.StiffnessLow)
                                        )
                                    }
                                },
                                onHorizontalDrag = { change, dragAmount ->
                                    // 4. Update offset (restricting to right-only swipe)
                                    val newOffset = (offsetX.value + dragAmount).coerceAtLeast(0f)
                                    scope.launch { offsetX.snapTo(newOffset) }
                                    change.consume() // Prevent parent (like a list) from scrolling
                                }
                            )
                        }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RectangleShape
                        )
                ) {
                    // Background revealed during swipe (e.g., a "Save" icon)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        BottomMostView(
                            description = "Save",
                            modifier = Modifier.padding(end = 24.dp)
                        )
                    }

                    // Foreground content that actually slides
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        TopMostView(
                            article = article
                        )
                    }
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

@Composable
private fun BottomMostView(
    description: String = "Save",
    modifier: Modifier = Modifier
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Save Icon",
            tint = Color.Red,
            modifier = modifier.size(24.dp)
        )

        Text(
            text = description,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp,
            modifier = modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun TopMostView (
    article: Article,
) {
    var isOpenCard by rememberSaveable { mutableStateOf(false) }
    if (isOpenCard) {
        NewsDetailCard(
            imageUrl = article.urlToImage ?: "",
            title = article.title,
            subtitle = article.description ?: "No description available",
            source = article.source?.name ?: "Unknown Source",
            publishedAt = Utils.extractDateFromTimestamp(article.publishedAt),
            author = article.author ?: "Unknown Author",
            description = article.content ?: "No content available",
            link = article.url,
            onShrinkClick = {
                isOpenCard = false
            }
        )
    } else {
        NewsListItem(
            imageDesc = article.description ?: "No description available",
            imageUrl = article.urlToImage ?: "",
            publishedAt = Utils.extractDateFromTimestamp(article.publishedAt),
            topic = article.title,
            onExpandClick = {
                isOpenCard = true
            }
        )
    }

}

@Preview
@Composable
fun SavedScreenPreview() {
    BuildView(

    )
}