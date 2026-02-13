package com.cristobal.gonews.utils

object Utils {
    fun extractDateFromTimestamp(timestamp: String): String {
        // Assumes timestamp is in the format "yyyy-MM-ddTHH:mm:ssZ"
        return timestamp.substringBefore('T')
    }
}