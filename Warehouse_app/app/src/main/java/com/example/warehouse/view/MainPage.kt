package com.example.warehouse.view

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.warehouse.view_models.AvtorizationVM
import com.example.warehouse.view_models.MainPageViewModel

@Composable
fun MainPage(navHost: NavHostController, viewModel: MainPageViewModel) {
    val ctx = LocalContext.current
    val worksList by viewModel.worksList.collectAsState()
    LazyColumn {
        items(worksList) { work ->
            Text(text = work.name, color = Color.Black) // отображение каждого элемента Work
        }
    }
}