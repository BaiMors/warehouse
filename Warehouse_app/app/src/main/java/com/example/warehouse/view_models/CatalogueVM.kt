package com.example.warehouse.view_models

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Tags
import com.example.warehouse.models.Users
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogueVM:MainViewModel() {
    private val _listWorks = _worksList
    private val _listFandoms = MutableStateFlow<List<Fandoms>>(emptyList())
    private val _listTags = MutableStateFlow<List<Tags>>(emptyList())
    private val _listAuthors = MutableStateFlow<List<Users>>(emptyList())
    var ListWorks: StateFlow<List<Works>> = _listWorks
    var ListFandoms: StateFlow<List<Fandoms>> = _listFandoms
    var ListTags: StateFlow<List<Tags>> = _listTags
    var ListAuthors: StateFlow<List<Users>> = _listAuthors

    private fun loadLists() {
        viewModelScope.launch {
            try {
                _listWorks.value = _worksList.value.sortedBy { it.date }
                _listFandoms.value = Constants.supabase.from("Fandoms").select().decodeList<Fandoms>().sortedBy { it.name }
                _listTags.value = Constants.supabase.from("Tags").select().decodeList<Tags>().sortedBy { it.name }
                _listAuthors.value = Constants.supabase.postgrest.rpc("my_users").decodeList<Users>().sortedBy { it.name }
                //_listAuthors.value = Constants.supabase.from("Users").select().decodeList<Users>().sortedBy { it.name }
            }
            catch (e: Exception){
                Log.e("MainPageViewModel", "Error fetching data: ${e.localizedMessage}")
            }
        }
    }
    init {
        //loadWorks()
        loadLists()
    }
}