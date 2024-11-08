package com.example.warehouse.models

import android.nfc.Tag
import kotlinx.serialization.Serializable

@Serializable
data class Work_tags(
    val id: String,
    val work: String,
    val tag: String,

    var tag1: Tags? = null,
    var work1: Works? = null
)
