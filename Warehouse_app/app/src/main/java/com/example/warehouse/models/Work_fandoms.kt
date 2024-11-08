package com.example.warehouse.models

import kotlinx.serialization.Serializable

@Serializable
data class Work_fandoms(
    val id: String,
    val work: String,
    val fandom: String,

    var fandom1: Fandoms? = null,
    var work1: Works? = null
)
