package com.example.warehouse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.warehouse.view.Avtorization
import com.example.warehouse.view.Catalogue
import com.example.warehouse.view.MainPage
import com.example.warehouse.view.Profile
import com.example.warehouse.view.Registration
import com.example.warehouse.view.Search
import com.example.warehouse.view.SearchOpen
import com.example.warehouse.view_models.AvtorizationVM
import com.example.warehouse.view_models.CatalogueVM
import com.example.warehouse.view_models.MainPageViewModel
import com.example.warehouse.view_models.MainViewModel
import com.example.warehouse.view_models.ProfileVM
import com.example.warehouse.view_models.SearchViewModel

/*Класс для перемещения по страницам*/
@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "MainPage")
    {
        composable("Avtorization"){
            Avtorization(navController, AvtorizationVM())
        }
        composable("Registration"){
            Registration(navController, AvtorizationVM())
        }
        composable("MainPage"){
            MainPage(navController, MainPageViewModel())
            //MainPage()
        }
        composable("Search"){
            Search(navController, SearchViewModel())
            //MainPage()
        }
        composable(
            "SearchOpen/{searchStr}",
            arguments = listOf(
                navArgument(name = "searchStr"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            SearchOpen(navController, SearchViewModel(), backStackEntry.arguments?.getString("searchStr"))
            //MainPage()
        }
        composable("Catalogue"){
            Catalogue(navController, CatalogueVM())
            //MainPage()
        }
        composable("Profile"){
            Profile(navController, ProfileVM())
            //MainPage()
        }
    }
}