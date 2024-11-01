package com.example.warehouse.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.warehouse.view_models.AvtorizationVM
import com.example.warehouse.view_models.MainPageViewModel

@Composable
fun MainPage(navHost: NavHostController, viewModel: MainPageViewModel.Companion) {
    var worksList = viewModel.create().getWorks()

}