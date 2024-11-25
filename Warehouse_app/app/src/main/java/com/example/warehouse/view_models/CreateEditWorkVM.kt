package com.example.warehouse.view_models

import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Tags
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

class CreateEditWorkVM: MainViewModel() {
    val _fandomsList = MutableStateFlow<List<Fandoms>>(emptyList())
    var fandomsList: StateFlow<List<Fandoms>> = _fandomsList
    val _tagsList = MutableStateFlow<List<Tags>>(emptyList())
    var tagsList: StateFlow<List<Tags>> = _tagsList

/*     fun getWorkData(work: String):Works{
        viewModelScope.launch {

            val selectedWork = worksList.value.first { it.id == work }
        }

    }*/
    fun getFT(){
        viewModelScope.launch {
            val fandomsFromDb =
                Constants.supabase.from("Fandoms").select().decodeList<Fandoms>()
            val tagsFromDb = Constants.supabase.from("Tags").select().decodeList<Tags>()
            _fandomsList.value = fandomsFromDb
            _tagsList.value = tagsFromDb
        }
    }

    fun saveWork(name: String, desc: String, status: String, num_ch: Int, tags: List<String>, fds: List<String>){
        viewModelScope.launch {
            try {
                Constants.supabase.from("Works").insert(
                    mapOf(
                        "name" to name, "description" to desc,
                        "status" to status, "image" to null,
                        "num_chapters" to num_ch, "author" to Constants.supabase.auth.currentUserOrNull()!!.id,
                        "date" to LocalDate.now(), "likes" to 0
                    )
                )
            }
            catch (e: Exception){
                println("Error on save edit work!")
                println(e.message.toString())
            }
        }
    }

    init {
        getFT()
    }
}