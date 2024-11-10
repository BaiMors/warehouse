package com.example.warehouse.view_models

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Gallery
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

class MainPageViewModel: MainViewModel() {
    //var text = "я пришел из mainpageviewmodel!"
    val _popularWorks = _worksList
    //var popularWorks: StateFlow<List<Works>> = _popularWorks
    var popularWorks: StateFlow<List<Works>> = _popularWorks

    private fun getPopularWorks(){
        val buf = _worksList.value.sortedBy { it.likes }.take(5)
        println("!!!!!!! ВЬЮ МОДЕЛЬ ")
        _popularWorks.value = _popularWorks.value.sortedBy { it.likes }.take(5)
    }

    init {
        loadWorks()
        getPopularWorks()
    }
}