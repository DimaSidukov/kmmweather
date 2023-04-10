package com.example.kmmweather.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSDateFormatter

actual fun LocalDateTime.format(format: String): String = iosFormatter(format)

fun LocalDateTime.iosFormatter(format: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = format
    return this.toNSDateComponents().date?.let {
        dateFormatter.stringFromDate(it)
    } ?: ""
}