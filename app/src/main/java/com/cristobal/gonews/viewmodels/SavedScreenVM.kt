package com.cristobal.gonews.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SavedScreenVM: ViewModel() {
    private val _savedArticles = MutableStateFlow( emptyList<com.cristobal.gonews.api_services.models.Article>())
    val savedArticles: StateFlow<List<com.cristobal.gonews.api_services.models.Article>> get() = _savedArticles

    private val _articleRepository = com.cristobal.gonews.db.repositories.ArticleRepository(
        com.cristobal.gonews.ApplicationContext.instance
    )

    init {
        fetchSavedArticles()
    }

    private fun fetchSavedArticles() {
        viewModelScope.launch (Dispatchers.IO) {
            _articleRepository.getRecentArticlesWithSource(50).collect {
                _savedArticles.value = it.map { articleWithSource ->
                    articleWithSource.article.copy().apply {
                        source = articleWithSource.source
                    }
                }
                Log.i( "SavedScreenVM", "Fetched ${it.size} saved articles from database")
            }
        }
    }
}