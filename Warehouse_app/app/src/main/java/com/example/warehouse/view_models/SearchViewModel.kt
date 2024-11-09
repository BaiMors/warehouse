package com.example.warehouse.view_models

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class SearchViewModel : MainViewModel() {
    fun filterWorks(searchStr: String){
        viewModelScope.launch {
            val allWorks = _worksList.value.filter { it.name.contains(searchStr, true) }
            _worksList.value = allWorks
        }
    }
    init {
        loadWorks()
    }
}
