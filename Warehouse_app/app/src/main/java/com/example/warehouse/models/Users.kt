package com.example.warehouse.models

import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val id: String,
    val name: String,
    val username: String,
    val description: String?,
    val title: String?,
    val image: String?,
)