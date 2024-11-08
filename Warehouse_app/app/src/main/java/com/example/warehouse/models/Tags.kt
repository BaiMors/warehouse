package com.example.warehouse.models

import kotlinx.serialization.Serializable

@Serializable
data class Tags(
    val id: String,
    val name: String,
)
