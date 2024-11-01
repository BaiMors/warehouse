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
import kotlinx.coroutines.withContext

class MainPageViewModel: ViewModel() {
    companion object{
        fun  create () : MainPageViewModel {
            return MainPageViewModel()
        }
    }
    var worksList by mutableStateOf<List<Works>>(listOf())

    @Composable
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
    }


}