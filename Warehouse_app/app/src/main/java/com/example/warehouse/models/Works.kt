package com.example.warehouse.models

import kotlinx.serialization.Serializable
import java.sql.Date
import java.sql.Timestamp

@Serializable
data class Works(
    val id: String,
    val name: String,
    val description: String,
    val author: String,
    val status: String,
    val num_chapters: Int,
    val date: String,
    val likes: Int,

    var author1: Users? = null,
    var fandoms: List<Work_fandoms>? = null,
    var tags: List<Work_tags>? = null,
    var gallery: List<Gallery>? = null
)
