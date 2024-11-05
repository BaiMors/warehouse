package com.example.warehouse.view_models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class MainPageViewModel: MainViewModel() {
/*    companion object{
        fun  create () : MainPageViewModel {
            return MainPageViewModel()
        }
    }*/
    //var worksList by mutableStateOf<List<Works>>(listOf())
    private val _worksList = MutableStateFlow<List<Works>>(emptyList())
    val worksList: StateFlow<List<Works>> = _worksList

    fun updateWorksList(newList: List<Works>) {
        _worksList.value = newList
    }

/*    @Composable
    fun getWorks():List<Works> {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                try {
                    worksList = Constants.supabase.from("Works").select().decodeList()
                    worksList.forEach{it->
                        println(it.name)
                    }
                } catch (e: Exception) {
                    println("Error get branches")
                    println(e.message)
                }
            }
        }
        return worksList
    }*/


}