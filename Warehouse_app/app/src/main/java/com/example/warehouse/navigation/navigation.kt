package com.example.warehouse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.warehouse.models.Works
import com.example.warehouse.view.Avtorization
import com.example.warehouse.view.Catalogue
import com.example.warehouse.view.CatalogueOpen
import com.example.warehouse.view.CategoryOpen
import com.example.warehouse.view.MainPage
import com.example.warehouse.view.Profile
import com.example.warehouse.view.ReadWork
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
        }
        composable("Search"){
            Search(navController, SearchViewModel())
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
        }
        composable("Catalogue"){
            Catalogue(navController, CatalogueVM())
        }
        composable(
            "CatalogueOpen/{category}",
            arguments = listOf(
                navArgument(name = "category"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            CatalogueOpen(navController, CatalogueVM(), backStackEntry.arguments?.getString("category"))
        }
        composable(
            "CategoryOpen/{category}/{item}",
            arguments = listOf(
                navArgument(name = "category"){
                    type = NavType.StringType
                },
                navArgument(name = "item"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            CategoryOpen(navController, CatalogueVM(), backStackEntry.arguments?.getString("category"), backStackEntry.arguments?.getString("item"))
        }
        composable("Profile"){
            Profile(navController, ProfileVM())
        }
/*        composable("ReadWork"){
            ReadWork()
        }*/
        composable(
            "ReadWork/{work}/{chapter}",
            //"ReadWork/{work}",
            arguments = listOf(
/*                navArgument(name = "work"){
                    type = NavType.SerializableType(Works::class.java)
                },*/
                navArgument(name = "work"){
                    type = NavType.StringType
                },
                navArgument(name = "chapter"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            //ReadWork(navController, MainViewModel(), backStackEntry.arguments?.getSerializable("work") as Works, backStackEntry.arguments?.getString("chapter"))
            ReadWork(navController, MainViewModel(), backStackEntry.arguments?.getString("work"), backStackEntry.arguments?.getString("chapter"))
        }
    }
}