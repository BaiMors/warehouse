package com.example.warehouse.models

import kotlinx.serialization.Serializable

@Serializable
data class Favorite_works(
    val id: String,
    val work: String,
    val user: String
)
