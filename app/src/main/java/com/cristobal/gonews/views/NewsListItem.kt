package com.cristobal.gonews.views

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun NewsListItem(
    imageUrl: String,
    imageDesc: String,
    publishedAt: String,
    topic: String,
    modifier: Modifier = Modifier,
    onExpandClick: () -> Unit = { }
) {
    val releasedDateStr = buildAnnotatedString {
        withStyle(style = androidx.compose.ui.text.SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Published At: ")
        }
        append(publishedAt)
    }

    val headerStr = buildAnnotatedString {
        withStyle(style = androidx.compose.ui.text.SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Topic: ")
        }
        append(topic)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        ImageWithStateMonitoring(
            imageUrl = imageUrl,
            contentDescription = imageDesc,
            modifier = Modifier
                .size(64.dp)
                .clip(RectangleShape)
                .align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Top)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(
                        text = releasedDateStr,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp
                    )
                }

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(26.dp)
                        .align(Alignment.Top)
                        .clickable {
                            onExpandClick()
                        }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = headerStr,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
            )
        }

    }
}

@Composable
fun NetworkImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,

        /* placeholder = painterResource(id = R.drawable.ic_launcher_background),
         error = painterResource(id = R.drawable.ic_launcher_background)*/
    )
}

@Composable
fun ImageWithStateMonitoring(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {

    var imageState by remember {
        mutableStateOf<AsyncImagePainter.State>(
            AsyncImagePainter.State.Loading(
                null
            )
        )
    }
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            modifier = Modifier
                .matchParentSize()
                .alpha(if (imageState is AsyncImagePainter.State.Success) 1f else 0f),
            contentScale = ContentScale.Crop,
            onLoading = {
                Log.d("ImageState", "Loading image: $imageUrl")
                imageState = it
            },
            onSuccess = {
                Log.d("ImageState", "Successfully loaded image: $imageUrl")
                imageState = it
            },
            onError = {
                Log.e("ImageState", "Error loading image: $imageUrl", it.result.throwable)
                imageState = it
            }
        )
        if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary,
                strokeWidth = 2.dp
            )
        } else if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                imageVector = Icons.Filled.Image,
                contentDescription = "Error loading image",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
    }
}


@Preview
@Composable
fun NewsListItemPreview() {
    NewsListItem(
        imageUrl = "https://example.com/sample-image.jpg",
        imageDesc = "Sample Image",
        publishedAt = "2024-06-01",
        topic = "Breaking News: Compose UI in Android is amazing!"
    )
}