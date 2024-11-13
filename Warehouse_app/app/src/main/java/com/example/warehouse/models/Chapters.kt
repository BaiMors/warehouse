package com.example.warehouse.models

import kotlinx.serialization.Serializable

@Serializable
data class Chapters(
    val id: String,
    val name: String,
    val content: String,
    val work: String,
    val service_data: String?
)
