package com.cristobal.gonews.db.repositories

import android.content.Context
import com.cristobal.gonews.api_services.models.Article
import com.cristobal.gonews.api_services.models.ArticleWithSource
import kotlinx.coroutines.flow.Flow

class ArticleRepository(context: Context) {
    private val articleDao = com.cristobal.gonews.db.rooms.DBBuilder
        .getInstance(context)
        .articleDao()
    private val sourceRepository = SourceRepository(context)

    suspend fun insertArticles(vararg articles: Article) {
        articles.forEach { article ->
            article.source?.let {
                if (it.id != null && it.name != null) {
                    sourceRepository.insertSource(it)
                }
                else if (it.id == null) {
                    val sourceId = it.name ?: ""
                    sourceRepository.insertSource(com.cristobal.gonews.api_services.models.Source(id = sourceId, name = it.name ?: ""))
                }
            }
        }
        val sanitizedArticle = articles.map { article ->
            article.copy(
                sourceId = article.source?.id ?: article.source?.name ?: "",
            )
        }

        articleDao.insertArticles(sanitizedArticle.toList())
    }

    fun getAllArticles(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }

    fun getRecentArticlesWithSource(limit: Int): Flow<List<ArticleWithSource>> {
        return articleDao.getRecentArticlesWithSource(limit)
    }

    suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article)
    }
}