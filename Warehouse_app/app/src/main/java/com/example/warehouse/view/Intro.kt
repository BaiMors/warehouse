package com.example.warehouse.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.warehouse.R
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.view_models.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun Intro(navHost: NavHostController, viewModel: MainViewModel){
    LaunchedEffect(Unit) {
        // Задержка на 2 секунды
        delay(100)
        //
/*        val editor = MainViewModel.PrefsHelper.getSharedPreferences().edit()
        editor.clear().apply()*/
        //val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        //val userEmail = sharedPreferences.getString("user_email", null)
        val userEmail = MainViewModel.PrefsHelper.getSharedPreferences().getString("user_email", null)

        if (userEmail != null) {
            // Перейти к главному экрану
            viewModel.loadWorks()
            navHost.navigate("MainPage"){
                popUpTo("Intro") { inclusive = true }
            }
        } else {
            // Показать экран авторизации
            navHost.navigate("Avtorization"){
                popUpTo("Intro") { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(DarkGreen)) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            Icon(
                painter = painterResource(R.drawable.home_avt),
                contentDescription = "",
                tint = Brown,
                //modifier = Modifier.padding(bottom = 30.dp)
            )
            Text(
                "Склад",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                color = LightBrown,
                modifier = Modifier
                    //.align(Alignment.CenterHorizontally)
                    //.padding(bottom = 50.dp)
            )
        }
    }
}