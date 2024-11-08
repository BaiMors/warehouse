package com.example.warehouse.models

import kotlinx.serialization.Serializable

@Serializable
data class Gallery(
    val id: String,
    val image: String,
    val work: String
)
