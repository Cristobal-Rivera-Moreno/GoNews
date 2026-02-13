package com.cristobal.gonews.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristobal.gonews.BuildConfig
import com.cristobal.gonews.api_services.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenVM: ViewModel() {
    private val obTag = HomeScreenVM::class.java.simpleName
    private val _newsList = MutableStateFlow<NewsResponse>(NewsResponse("", 0, emptyList()))
    val newsList: StateFlow<NewsResponse> get() = _newsList

    init {
        requestNews()
    }

    private fun requestNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = com.cristobal.gonews.api_services.models.RetrofitService.getInstance()
                    .getNewsRequestsInterface()
                    .getTopHeadlines("us", BuildConfig.NEWS_API_KEY)
                _newsList.value = result
            } catch (e: Exception) {
                Log.e(obTag, "Error fetching news: ${e.message}")
            }
        }
    }

    fun saveArticle(article: com.cristobal.gonews.api_services.models.Article) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val repository = com.cristobal.gonews.db.repositories.ArticleRepository(
                    com.cristobal.gonews.ApplicationContext.instance
                )
                repository.insertArticles(article)
                Log.i(obTag, "Article saved: ${article.title}")
            } catch (e: Exception) {
                Log.e(obTag, "Error saving article: ${e.message}")
            }
        }
    }
}