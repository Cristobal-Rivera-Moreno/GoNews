package com.cristobal.gonews.api_services.models


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    val articles: List<Article>
)

@Entity(tableName = "articles",
    foreignKeys = [
        ForeignKey(
            entity = Source::class,
            parentColumns = ["id"],
            childColumns = ["sourceId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    val sourceId: String? = "",
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String,
    val content: String?
) {
    @Ignore
    var source: Source? = null
}

@Entity(tableName = "sources")
data class Source(
    @PrimaryKey val id: String,
    val name: String
)

data class ArticleWithSource(
    @Embedded val article: Article,
    @Relation(
        parentColumn = "sourceId",
        entityColumn = "id"
    )
    val source: Source?
)