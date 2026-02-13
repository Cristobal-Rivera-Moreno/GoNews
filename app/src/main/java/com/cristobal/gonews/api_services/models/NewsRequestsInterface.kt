package com.cristobal.gonews.api_services.models

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRequestsInterface {
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String, @Query("apiKey") apiKey: String): NewsResponse

}