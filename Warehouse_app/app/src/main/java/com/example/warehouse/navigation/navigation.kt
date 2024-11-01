package com.example.warehouse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warehouse.view.Avtorization
import com.example.warehouse.view.MainPage
import com.example.warehouse.view.Registration
import com.example.warehouse.view_models.AvtorizationVM
import com.example.warehouse.view_models.MainPageViewModel
import com.example.warehouse.view_models.MainViewModel

/*Класс для перемещения по страницам*/
@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController,//контроллер реагирующий и отвечающий за перемещения
        startDestination = "MainPage")
    {
        composable("Avtorization"){
            Avtorization(navController, AvtorizationVM)
        }
        composable("Registration"){
            Registration(navController, AvtorizationVM)
        }
        composable("MainPage"){
            MainPage(navController, MainPageViewModel)
        }
    }
}