package com.example.warehouse.service

data class UploadOptions(
    val contentType: String? = null,
    val cacheControl: String? = null,
    val upsert: Boolean = false
)
