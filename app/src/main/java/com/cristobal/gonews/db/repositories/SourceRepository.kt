package com.cristobal.gonews.db.repositories

import android.content.Context

class SourceRepository(context: Context) {
    private val sourceDao = com.cristobal.gonews.db.rooms.DBBuilder
        .getInstance(context)
        .sourceDao()

    suspend fun insertSource(source: com.cristobal.gonews.api_services.models.Source) {
        sourceDao.insertSource(source)
    }

    suspend fun insertSources(vararg sources: com.cristobal.gonews.api_services.models.Source) {
        sourceDao.insertSources(sources.toList())
    }

    fun getAllSources(): kotlinx.coroutines.flow.Flow<List<com.cristobal.gonews.api_services.models.Source>> {
        return sourceDao.getAllSources()
    }
}