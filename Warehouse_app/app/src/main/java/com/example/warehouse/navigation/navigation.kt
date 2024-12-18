package com.example.warehouse.navigation

import android.content.Context
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
import com.example.warehouse.view.CreateEditWork
import com.example.warehouse.view.FavotitesOpen
import com.example.warehouse.view.Intro
import com.example.warehouse.view.MainPage
import com.example.warehouse.view.MyOpen
import com.example.warehouse.view.Profile
import com.example.warehouse.view.ReadWork
import com.example.warehouse.view.Registration
import com.example.warehouse.view.Search
import com.example.warehouse.view.SearchOpen
import com.example.warehouse.view.UpdateProfile
import com.example.warehouse.view_models.AvtorizationVM
import com.example.warehouse.view_models.CatalogueVM
import com.example.warehouse.view_models.CreateEditWorkVM
import com.example.warehouse.view_models.MainPageViewModel
import com.example.warehouse.view_models.MainViewModel
import com.example.warehouse.view_models.ProfileVM
import com.example.warehouse.view_models.SearchViewModel

/*функция для перемещения по страницам*/
@Composable
fun Navigation(viewModel: MainViewModel, context: Context) {
/*    // Получение сохраненного токена
    var sharedPreferences = viewModel.sharedPreferences
    sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userEmail = viewModel.sharedPreferences!!.getString("user_email", null)


    if (userEmail != null) {
        startPage = "MainPage"
    } else {
        startPage = "Avtorization"
    }*/


    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "Intro")
    {
        composable("Intro"){
            Intro(navController, viewModel)
        }
        composable("Avtorization"){
            Avtorization(navController, AvtorizationVM())
        }
        composable("Registration"){
            Registration(navController, AvtorizationVM())
        }
        composable("MainPage"){
            MainPage(navController, MainPageViewModel(),MainViewModel())
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
            Profile(navController, ProfileVM(), MainViewModel())
        }
        composable("UpdateProfile"){
            UpdateProfile(navController, ProfileVM(), MainViewModel())
        }
        composable("FavotitesOpen"){
            FavotitesOpen(navController, MainViewModel(), ProfileVM())
        }
        composable("MyOpen"){
            MyOpen(navController, MainViewModel(), ProfileVM())
        }
        composable(
            "CreateEditWork/{work}",
            //"ReadWork/{work}",
            arguments = listOf(
                navArgument(name = "work"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            CreateEditWork(navController, CreateEditWorkVM(), backStackEntry.arguments?.getString("work"))
        }
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