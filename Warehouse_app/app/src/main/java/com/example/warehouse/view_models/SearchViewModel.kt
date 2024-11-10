package com.example.warehouse.view_models

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Works
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class SearchViewModel : MainViewModel() {
    val _foundWorks = _worksList
    var foundWorks: StateFlow<List<Works>> = _foundWorks

/*    @Composable
    fun getSnackbar(){
        val snackbarHostState = SnackbarHostState()
        val scope = rememberCoroutineScope()
        forGetSnackBar(scope, snackbarHostState)
    }

    fun forGetSnackBar(scope: CoroutineScope, snackbarHostState: SnackbarHostState){
        viewModelScope.launch {
            scope.launch {
                snackbarHostState.showSnackbar("Сообщение")
            }
        }
    }*/

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
