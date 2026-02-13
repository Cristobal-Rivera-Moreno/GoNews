package com.cristobal.gonews.db.rooms

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [com.cristobal.gonews.api_services.models.Article::class, com.cristobal.gonews.api_services.models.Source::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao(): com.cristobal.gonews.db.ArticleDao
    abstract fun sourceDao(): com.cristobal.gonews.db.SourceDao
}