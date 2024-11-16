package com.example.warehouse.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.models.Users
import com.example.warehouse.service.Constants
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AvtorizationVM:ViewModel(){

    sealed class Result {
        data class Success(val user: Any) : Result()  // Успех
        data class Error(val message: String) : Result()  // Ошибка
    }

    // Состояние для результата авторизации
    private val _authResult = MutableStateFlow<Result?>(null)
    val AuthResult: StateFlow<Result?> = _authResult

    private val _regResult = MutableStateFlow<Result?>(null)
    val RegResult: StateFlow<Result?> = _regResult


    fun onSignInEmailPassword(emailUser: String, passwordUser: String) {
        viewModelScope.launch {
            try {
                val user = Constants.supabase.auth.signInWith(Email) {
                    email = emailUser
                    password = passwordUser
                }
                println(user.toString())
                println(Constants.supabase.auth.currentUserOrNull()!!.id)
                println("Success authorization")
                _authResult.value = Result.Success(user)
            } catch (e: Exception) {
                println("Error")
                println(e.message.toString())
                _authResult.value = Result.Error(e.message.toString())  // Ошибка входа
            }
        }
    }
    fun onSignUpEmail(emailUser: String, passwordUser: String, name: String, username: String, description: String) {
        viewModelScope.launch {
            try{
                val userAuth =  Constants.supabase.auth.signUpWith(Email) {
                    email = emailUser
                    password = passwordUser
                }

                //if (userAuth!!.id.isNullOrEmpty()){
                    val userPublic = Constants.supabase.from("Users").insert(
                        mapOf(
                            "name" to name, "username" to username,
                            "description" to  description, "image" to null,
                            "title" to null, "id" to Constants.supabase.auth.currentUserOrNull()!!.id
                        )
                    )
                //}

                println(userAuth.toString())
                //println(userPublic.toString())
                println(Constants.supabase.auth.currentUserOrNull()!!.id)
                println("Success registration")
                _regResult.value = Result.Success(user = userPublic!!)
            }
            catch (e: Exception) {
                println("Error")
                println(Constants.supabase.auth.currentUserOrNull()!!.id)
                println(e.message.toString())
                _regResult.value = Result.Error(e.message.toString())
            }

        }
    }
}