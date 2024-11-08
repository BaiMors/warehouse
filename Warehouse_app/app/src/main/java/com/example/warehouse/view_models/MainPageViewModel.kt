package com.example.warehouse.view_models

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Tags
import com.example.warehouse.models.Users
import com.example.warehouse.models.Work_fandoms
import com.example.warehouse.models.Work_tags
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPageViewModel: ViewModel() {
    var text = "я пришел из mainpageviewmodel!"

    private val _worksList = MutableStateFlow<List<Works>>(emptyList())
    var worksList: StateFlow<List<Works>> = _worksList
    private fun loadWorks(){
        viewModelScope.launch {
            try {
                val worksFromDb = Constants.supabase.from("Works").select().decodeList<Works>()
                val authorsFromDb = Constants.supabase.from("Users").select().decodeList<Users>()
                val wfFromDb = Constants.supabase.from("Work_fandoms").select().decodeList<Work_fandoms>()
                val wtFromDb = Constants.supabase.from("Work_tags").select().decodeList<Work_tags>()
                val fandomsFromDb = Constants.supabase.from("Fandoms").select().decodeList<Fandoms>()
                val tagsFromDb = Constants.supabase.from("Tags").select().decodeList<Tags>()

                _worksList.value = worksFromDb.map { work ->
                    val au = authorsFromDb.find {it.id == work.author }
                    val wf = wfFromDb.map { work_fandom ->
                        val f = fandomsFromDb.find { it.id == work_fandom.id }
                        work_fandom.fandom1 = f
                        work_fandom
                    }
                    val wt = wtFromDb.map { work_tag ->
                        val t = tagsFromDb.find { it.id == work_tag.id }
                        work_tag.tag1 = t
                        work_tag
                    }
                    work.author1 = au
                    work.fandoms = wf
                    work.tags = wt
                    work
                }
            }
            catch (e: Exception) {
                Log.e("MainPageViewModel", "Error fetching data: ${e.localizedMessage}")
            }
        }
    }
    init {
        loadWorks()
    }
}