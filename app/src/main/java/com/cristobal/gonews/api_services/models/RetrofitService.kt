package com.cristobal.gonews.api_services.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    private val retrofietClient: Retrofit

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private var _instance: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (_instance == null) {
                _instance = RetrofitService()
            }
            return _instance!!
        }
    }

    init {
        retrofietClient = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    fun getNewsRequestsInterface(): NewsRequestsInterface {
        return retrofietClient.create(NewsRequestsInterface::class.java)
    }

}