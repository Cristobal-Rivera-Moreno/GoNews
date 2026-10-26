package com.cristobal.gonews.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cristobal.gonews.api_services.models.Article
import com.cristobal.gonews.api_services.models.ArticleWithSource
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC LIMIT :limit")
    fun getRecentArticlesWithSource(limit: Int): Flow<List<ArticleWithSource>>

    @Delete
    suspend fun deleteArticle(article: Article)
}