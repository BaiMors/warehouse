package com.example.warehouse.view_models

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.R
import com.example.warehouse.models.Chapters
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
import io.github.jan.supabase.postgrest.query.request.RpcRequestBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

open class MainViewModel : ViewModel() {
    //var sharedPreferences: SharedPreferences? = null

    //объект для SharedPreferences (хранения данных о сессии), чтобы можно было обращаться к нему в любом месте приложения
    object PrefsHelper {
        private lateinit var sharedPreferences: SharedPreferences

        fun init(context: Context) {
            sharedPreferences = context.getSharedPreferences("user_email", Context.MODE_PRIVATE)
        }

        fun getSharedPreferences(): SharedPreferences = sharedPreferences
    }

    var isLiked by mutableStateOf(false)
    //var currentWorkId by mutableStateOf(mutableListOf(""))

    val _worksList = MutableStateFlow<List<Works>>(emptyList())
    var worksList: StateFlow<List<Works>> = _worksList

    val _isDataLoaded = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded

    /*    private var cachedData: List<Works>? = null
        fun getDataFromCache(): List<Works>{
            return cachedData ?: emptyList()
        }
        fun saveToCache(data: List<Works>){
            cachedData = data
        }

        @Composable
        fun getWorks(){
            val data = remember {
                mutableStateOf<List<Works>>(emptyList())
            }
            LaunchedEffect(Unit) {
                val cachedData = getDataFromCache()
                if (cachedData.isNullOrEmpty()){
                    val freshData = _worksList.value
                    data.value = freshData
                    saveToCache(freshData)
                }
                else{
                    data.value = cachedData
                }
            }
        }*/



    fun loadWorks() {
        viewModelScope.launch {
            try {
                val worksFromDb = Constants.supabase.from("Works").select().decodeList<Works>()
                val authorsFromDb = Constants.supabase.from("Users").select().decodeList<Users>()
                val wfFromDb =
                    Constants.supabase.from("Work_fandoms").select().decodeList<Work_fandoms>()
                val wtFromDb = Constants.supabase.from("Work_tags").select().decodeList<Work_tags>()
                val fandomsFromDb =
                    Constants.supabase.from("Fandoms").select().decodeList<Fandoms>()
                val tagsFromDb = Constants.supabase.from("Tags").select().decodeList<Tags>()
                val galleryFromDb =
                    Constants.supabase.from("Gallery").select().decodeList<Gallery>()
                val chaptersFromDb =
                    Constants.supabase.from("Chapters").select().decodeList<Chapters>()
                val favworksFromDb =
                    Constants.supabase.from("Favorite_works").select().decodeList<Favorite_works>()

                _worksList.value = worksFromDb.map { work ->
                    val au = authorsFromDb.find { it.id == work.author }
                    wfFromDb.map { work_fandom ->
                        val f = fandomsFromDb.find { it.id == work_fandom.fandom }
                        work_fandom.fandom1 = f
                        work_fandom
                    }
                    wtFromDb.map { work_tag ->
                        val t = tagsFromDb.find { it.id == work_tag.tag }
                        work_tag.tag1 = t
                        work_tag
                    }
                    work.author1 = au
                    work.fandoms = wfFromDb.filter { it.work == work.id }
                    work.tags = wtFromDb.filter { it.work == work.id }
                    work.gallery = galleryFromDb.filter { it.work == work.id }
                    work.chapters = chaptersFromDb.filter { it.work == work.id }
                    val fv = favworksFromDb.filter { it.work == work.id && it.user == Constants.supabase.auth.currentUserOrNull()!!.id }
                    val fv2 = favworksFromDb.filter { it.work == work.id }
                    work.userLiked = fv2
                    work.isliked = fv.isNotEmpty()
                    work
                }
                //println("название главы?? " + _worksList.value[0].chapters!![0].name)
                _isDataLoaded.value = true
            } catch (e: Exception) {
                Log.e("MainPageViewModel", "Error fetching data: ${e.localizedMessage}")
            }
        }
    }




    init {
        loadWorks()
    }
}