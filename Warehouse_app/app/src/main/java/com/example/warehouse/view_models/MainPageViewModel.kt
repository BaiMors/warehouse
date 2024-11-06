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
import com.example.warehouse.models.Users
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
                _worksList.value = worksFromDb.map { work ->
                    val au = authorsFromDb.find {it.id == work.author }
                    work.author1 = au
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