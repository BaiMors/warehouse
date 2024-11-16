package com.example.warehouse.view_models

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Chapters
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Gallery
import com.example.warehouse.models.Tags
import com.example.warehouse.models.Users
import com.example.warehouse.models.Work_fandoms
import com.example.warehouse.models.Work_tags
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileVM:MainViewModel(){
    val _userList = MutableStateFlow<List<Users>>(emptyList())
    var userList: StateFlow<List<Users>> = _userList

    val _isUserLoaded = MutableStateFlow(false)
    val isUserLoaded: StateFlow<Boolean> = _isUserLoaded

    fun loadUsers(){
        viewModelScope.launch {
            try {
                val authorsFromDb = Constants.supabase.from("Users").select().decodeList<Users>()
                val favworksFromDb = Constants.supabase.from("Favorite_works").select().decodeList<Works>()
                if (_isDataLoaded.value)
                {
                    _userList.value = authorsFromDb.map { formap ->
                        val mv = _worksList.value.filter { it.author == formap.id }
                        val fv = favworksFromDb.filter { it.author == formap.id }
                        formap.my_works = mv
                        formap.fav_works = fv
                        formap
                    }
                    _isUserLoaded.value = true
                }
            }
            catch (e: Exception) {
                Log.e("MainPageViewModel", "Error fetching data: ${e.localizedMessage}")
            }
        }
    }
    init {
        loadWorks()
        loadUsers()
    }
}