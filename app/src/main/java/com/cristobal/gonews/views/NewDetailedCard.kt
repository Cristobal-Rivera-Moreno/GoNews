package com.cristobal.gonews.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun NewsDetailCard(
    imageUrl: String,
    title: String,
    subtitle: String,
    source: String,
    publishedAt: String,
    author: String,
    description: String,
    link: String,
    modifier: Modifier = Modifier,
    onShrinkClick: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "News Image",
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 8.dp),
            )

            BubbleBackground(
                modifier = Modifier
                    .weight(1f)
            ) {
                Column (
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BoldUnderlinedText(
                            text = title,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            underlineColor = MaterialTheme.colorScheme.secondary,
                            underlineThickness = 4f, // Adjust thickness as needed,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        )

                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Expand",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .size(26.dp)
                                .align(Alignment.Top)
                                .fillMaxWidth(0.1f)
                                .clickable {
                                    onShrinkClick()
                                }
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = subtitle,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp,
                    )

                }
            }
        }
        Spacer(Modifier.height(8.dp))
        BubbleBackground(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column (
                modifier = Modifier
                    .padding(10.dp)
            ) {
                SectionHeader("Source")
                Spacer(Modifier.height(8.dp))
                SectionText(source)
            }
        }
        Spacer(Modifier.height(8.dp))
        BubbleBackground(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                SectionHeader("Published At")
                Spacer(Modifier.height(8.dp))
                SectionText(publishedAt)
            }
        }
        Spacer(Modifier.height(8.dp))
        BubbleBackground(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                SectionHeader("Author")
                Spacer(Modifier.height(8.dp))
                SectionText(author)
            }
        }
        Spacer(Modifier.height(8.dp))
        BubbleBackground(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                SectionHeader("Description")
                Spacer(Modifier.height(8.dp))
                SectionText(description)
            }
        }
        Spacer(Modifier.height(8.dp))
        BubbleBackground(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                SectionHeader("For Further Info Click Below")
                Spacer(Modifier.height(8.dp))
                SelectionContainer {
                    Text(
                        text = link,
                        color = Color(0xFF64B5F6),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    BoldUnderlinedText(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        underlineColor = MaterialTheme.colorScheme.secondary,
        underlineThickness = 4f // Adjust thickness as needed
    )
}

@Composable
private fun SectionText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 14.sp
    )
}

@Composable
fun BubbleBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.radialGradient(
                    colors = listOf( MaterialTheme.colorScheme.primary,Color.White,),
                    center = Offset.Unspecified, // Will be centered by default
                    radius = 6000f // Adjust as needed for your layout
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        content = content
    )
}
@Composable
private fun BoldUnderlinedText(
    text: String,
    color: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: androidx.compose.ui.unit.TextUnit = 16.sp,
    underlineColor: Color = Color.White,
    underlineThickness: Float = 4f, // in pixels
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        modifier = modifier.drawBehind {
            val y = size.height - underlineThickness / 2
            drawLine(
                color = underlineColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = underlineThickness
            )
        }
    )
}

@Preview
@Composable
fun NewsDetailCardPreview() {
    NewsDetailCard(
        imageUrl = "https://example.com/image.jpg",
        title = "Sample News Title sanbhgsahsgahgshgshgahsghashsaghgs",
        subtitle = "This is a sample subtitle for the news article.",
        source = "Example News Source",
        publishedAt = "2024-06-01",
        author = "John Doe",
        description = "This is a sample description of the news article. It provides more details about the content of the news.",
        link = "https://example.com/full-article"
    )
}