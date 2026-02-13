package com.cristobal.gonews.db.rooms

import android.content.Context
import androidx.room.Room

object DBBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "articles-database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}