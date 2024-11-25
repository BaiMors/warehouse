package com.example.warehouse.view_models

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Tags
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
    init {
        getFT()
    }
}