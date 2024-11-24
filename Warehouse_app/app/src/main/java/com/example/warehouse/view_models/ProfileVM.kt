package com.example.warehouse.view_models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Chapters
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Favorite_works
import com.example.warehouse.models.Gallery
import com.example.warehouse.models.Tags
import com.example.warehouse.models.Users
import com.example.warehouse.models.Work_fandoms
import com.example.warehouse.models.Work_tags
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileVM:MainViewModel(){
    sealed class Result {
        data class Success(val user: Any) : Result()  // Успех
        data class Error(val message: String) : Result()  // Ошибка
    }

    val _userList = MutableStateFlow<List<Users>>(emptyList())
    var userList: StateFlow<List<Users>> = _userList

    val _isUserLoaded = MutableStateFlow(false)
    val isUserLoaded: StateFlow<Boolean> = _isUserLoaded

    private val _upsertResult = MutableStateFlow<Result?>(null)
    val upsertResult: StateFlow<Result?> = _upsertResult

/*    //region View State
    private val _albumViewState: MutableStateFlow<Users> = MutableStateFlow(Users())
    // exposes the ViewState to the composable view
    val viewStateFlow: StateFlow<Users>
        get() = _albumViewState
    // endregion*/

    fun loadUsers(){
        viewModelScope.launch {
            try {
                val authorsFromDb = Constants.supabase.from("Users").select().decodeList<Users>()
                println("получили лист юзеров")
                val favworksFromDb = Constants.supabase.from("Favorite_works").select().decodeList<Favorite_works>()
                println("получили лист любимых работ")
                println("загрузились ли данные о работах "+_isDataLoaded.value)
                //if (_isDataLoaded.value)
                //{
                    _userList.value = authorsFromDb.map { formap ->
                        val mv = _worksList.value.filter { it.author == formap.id }
                        val fv = _worksList.value.filter { it.id == formap.id }
                        formap.my_works = mv
                        println("получили свои работы")
                        formap.fav_works = fv
                        println("получили любимые работы")
                        formap
                    }
                    println("пользователь должен загрузиться")
                    _isUserLoaded.value = true
                //}
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

    fun updateUserProfile(
        ctx: Context,
        newName: String?,
        newUsername: String?,
        newEmail: String?,
        newPassword: String?,
        newDesc: String?,
        title: String?,
        image: String?
    ) {//y_k_e_s //Standing next to you~
        println("enter upd")
        viewModelScope.launch {
            try {
                if (isUserLoaded.value == true)
                {
                    println("!!!isUserLoaded.value "+isUserLoaded.value)
                    val curUsData = userList.value.find { it.id == Constants.supabase.auth.currentUserOrNull()!!.id }
                    val toUpsert = Users(
                        id = Constants.supabase.auth.currentUserOrNull()!!.id,
                        name = newName!!,
                        username = newUsername!!,
                        description = newDesc,
                        image = image,
                        title = title)
                    Constants.supabase.from("Users").upsert(
                        toUpsert
                    ) {
                        filter {
                            eq("id", Constants.supabase.auth)
                        }
                    }//ardaismine@yandex.ru
                    if (newEmail != Constants.supabase.auth.currentUserOrNull()!!.email && newEmail != null && newEmail != "")
                    {
                        try {
                            Constants.supabase.auth.updateUser {
                                email = newEmail
                            }
                        }
                        catch (e: Exception){
                            println("Error")
                            println(e.message.toString())
                            Toast.makeText(ctx, "Ошибка: ${e.message.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    if (newPassword != null && newPassword != ""){
                        try {
                            Constants.supabase.auth.updateUser {
                                password = newPassword
                            }
                        }
                        catch (e: Exception){
                            println("Error")
                            println(e.message.toString())
                            Toast.makeText(ctx, "Ошибка: ${e.message.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    println("Success updating")
                    Toast.makeText(ctx, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }
    }
}