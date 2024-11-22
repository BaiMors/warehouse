package com.example.warehouse.models

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val id: String,
    val name: String,
    val username: String,
    val description: String?,
    val title: String?,
    val image: String?,

    var my_works: List<Works>? = null,
    var fav_works: List<Works>? = null,
    val tempFileUrl: String? = null,
    val selectedPicture: ImageBitmap? = null
)
