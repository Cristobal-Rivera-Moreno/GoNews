package com.cristobal.gonews

import android.app.Application

class ApplicationContext: Application() {

    companion object {
        private var _instance: ApplicationContext? = null
        val instance: ApplicationContext
            get() = _instance ?: throw IllegalStateException("ApplicationContext not initialized")
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }
}