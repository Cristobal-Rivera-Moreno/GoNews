package com.cristobal.gonews.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.cristobal.gonews.api_services.models.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(article: Source)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(articles: List<Source>)

    @androidx.room.Query("SELECT * FROM sources")
    fun getAllSources(): Flow<List<Source>>
}