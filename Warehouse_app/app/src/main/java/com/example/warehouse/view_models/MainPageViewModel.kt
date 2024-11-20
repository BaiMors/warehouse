package com.example.warehouse.view_models

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.R
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Favorite_works
import com.example.warehouse.models.Gallery
import com.example.warehouse.models.Tags
import com.example.warehouse.models.Users
import com.example.warehouse.models.Work_fandoms
import com.example.warehouse.models.Work_tags
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import com.example.warehouse.ui.theme.Brown
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class MainPageViewModel: MainViewModel() {
    //var text = "я пришел из mainpageviewmodel!"
    //val _popularWorks = _worksList
    //var popularWorks: StateFlow<List<Works>> = _popularWorks
    //var popularWorks: StateFlow<List<Works>> = worksList

/*    private fun getPopularWorks(){
        _popularWorks.value  = _worksList.value.sortedByDescending { it.likes }.take(5)
        println("!!!!!!! ВЬЮ МОДЕЛЬ ")
        print(popularWorks.value.size)
    }*/

    /*    fun toggleLike(workId: String, userId: String) {
        viewModelScope.launch {
            if (isLiked) {
                //currentWorkId.remove(workId)  //&& currentWorkId[currentWorkId.size-1] == workId
                Constants.supabase.from("Favorite_works").delete {
                    select()
                    filter {
                        eq("work", workId)
                        eq("user", userId)
                    }
                }
                val jsonObject: JsonObject = buildJsonObject {
                    put("workid", workId)
                }
                Constants.supabase.postgrest.rpc("remove_likes", jsonObject)
            } else {
                Constants.supabase.from("Favorite_works").insert(
                    mapOf(
                        "work" to workId, "user" to userId,
                    )
                )
                val jsonObject: JsonObject = buildJsonObject {
                    put("workid", workId)
                }
                Constants.supabase.postgrest.rpc("add_likes", jsonObject)
            }
            isLiked = !isLiked
        }
    }*/
    var ic by mutableStateOf(false)


    fun toggleLike(workId: String, userId: String, currentIsNotLiked: Boolean) {
        viewModelScope.launch {
            println("я зашел в toggleLike, чтобы лайкнуть работу " + workId)
            if (currentIsNotLiked == false) {
                // Удаление из избранного
                Constants.supabase.from("Favorite_works").delete {
                    select()
                    filter {
                        eq("work", workId)
                        eq("user", userId)
                    }
                }

                // Уменьшение количества лайков через вызов RPC
                val jsonObject: JsonObject = buildJsonObject {
                    put("workid", workId)
                }
                Constants.supabase.postgrest.rpc("remove_likes", jsonObject)
            }
            else {
                // Добавление в избранное
                Constants.supabase.from("Favorite_works").insert(
                    mapOf("work" to workId, "user" to userId)
                )

                // Увеличение количества лайков через вызов RPC
                val jsonObject: JsonObject = buildJsonObject {
                    put("workid", workId)
                }
                Constants.supabase.postgrest.rpc("add_likes", jsonObject)
            }

/*            val updatedList = popularWorks.value.map { work ->
                if (work.id == workId) {
                    // Логика добавления/удаления лайка и обновления поля isLiked
                    val newIsLiked = !work.isliked
                    // Обновите запись в базе данных здесь
                    work.copy(isliked = newIsLiked)
                } else {
                    work
                }
            }
            popularWorks = updatedList*/
        }
    }


    /*    @Composable
        fun LikeButton(
            isLiked: Boolean,
            onLikeClick: () -> Unit,
            curid: List<String>,
            workId: String
        ) {
            IconButton(onClick = onLikeClick) {
                Box {
                    Icon(
                        painter = if (isLiked) painterResource(R.drawable.filled)
                        //else if (!isLiked && curid.contains(workId)) painterResource(R.drawable.filled)
                        else painterResource(R.drawable.outlined),
                        contentDescription = if (isLiked) "Liked" else "Not Liked",
                        tint = Brown,
                        modifier = Modifier
                                .align(Alignment.TopEnd)
                                //.padding(end = 12.dp, top = 15.dp)
                            .width(40.dp)
                            .height(41.dp)
                    )
                }
            }
        }*/

    @Composable
    fun LikeButton(
        il: Boolean,
        onLikeClick: () -> Unit
    ) {
        IconButton(onClick = onLikeClick) {
            Icon(
                painter = if (il) painterResource(R.drawable.filled) else painterResource(R.drawable.outlined),
                contentDescription = if (il) "Liked" else "Not Liked",
                tint = Brown,
                modifier = Modifier
                    .width(40.dp)
                    .height(41.dp)
            )
        }
    }

    init {
        loadWorks()
        //getPopularWorks()
    }
}