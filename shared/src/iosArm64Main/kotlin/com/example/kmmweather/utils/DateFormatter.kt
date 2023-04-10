package com.example.kmmweather.utils

import kotlinx.datetime.*
import platform.Foundation.NSDateFormatter

actual fun LocalDateTime.format(format: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = format
    return toInstant(TimeZone.currentSystemDefault()).toNSDate().let {
        dateFormatter.stringFromDate(it)
    }
}