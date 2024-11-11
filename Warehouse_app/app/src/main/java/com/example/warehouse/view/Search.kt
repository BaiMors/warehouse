package com.example.warehouse.view

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.warehouse.R
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.ui.theme.LightGreen
import com.example.warehouse.view_models.MainPageViewModel
import com.example.warehouse.view_models.SearchViewModel
import kotlinx.coroutines.launch

@SuppressLint("ShowToast")
@Composable
fun Search(navHost: NavHostController, viewModel: SearchViewModel){
    val searchStr = remember { mutableStateOf("") }
    val ctx = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkGreen)
    ){
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Box(modifier = Modifier.align(Alignment.Center).fillMaxWidth().padding(start = 21.dp, end = 21.dp)) {
                TextField(
                    value = searchStr.value,
                    textStyle = TextStyle(fontSize = 15.sp),
                    onValueChange = {
                        newText ->
                        if (searchStr.value.length < 37){
                            searchStr.value = newText
                        }
                        else{
                            Toast.makeText(ctx, "Упс! Запрос слишком длинный", Toast.LENGTH_LONG)
                        }
                         },
                    maxLines = 1,
                    placeholder = { Text(text = "Поиск по работам...") },
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp).fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = LightBrown,
                        unfocusedTextColor = LightBrown,
                        focusedContainerColor = LightGreen,
                        unfocusedContainerColor = LightGreen,
                        focusedPlaceholderColor = LightGreen,
                        unfocusedPlaceholderColor = LightBrown,
                        focusedIndicatorColor = LightGreen,
                        unfocusedIndicatorColor = LightGreen
                    ),
                    //123456789 123456789 123456789 1234567
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "",
                            tint = LightBrown,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clickable { navHost.navigate("SearchOpen/${searchStr.value}") }
                            .padding(end = 11.dp)
                        )
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(57.dp)
                    .border(1.dp, DarkGreen)
                    .align(Alignment.BottomCenter)
                    .background(LightGreen),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(modifier = Modifier
                    .background(LightGreen)
                    .border(2.dp, LightGreen)
                    .height(57.dp)
                    .weight(1f).clickable { navHost.navigate("MainPage") }) {
                    Icon(
                        painter = painterResource(R.drawable.home),
                        contentDescription = "",
                        tint = LightBrown,
                        modifier = Modifier
                            .align(Alignment.Center)
                        //.padding(end = 21.dp, bottom = 25.dp)
                    )
                }
                Box(modifier = Modifier
                    .background(DarkGreen)
                    .border(2.dp, LightGreen)
                    .height(57.dp)
                    .weight(1f).clickable { navHost.navigate("Search") }) {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "",
                        tint = Brown,
                        modifier = Modifier
                            .align(Alignment.Center)
                        //.padding(end = 21.dp, bottom = 25.dp)
                    )
                }
                Box(modifier = Modifier
                    .background(LightGreen)
                    .border(1.dp, LightGreen)
                    .height(57.dp)
                    .weight(1f).clickable { navHost.navigate("Catalogue") }) {
                    Icon(
                        painter = painterResource(R.drawable.catalogue),
                        contentDescription = "",
                        tint = LightBrown,
                        modifier = Modifier
                            .align(Alignment.Center)
                        //.padding(end = 21.dp, bottom = 25.dp)
                    )
                }
                Box(modifier = Modifier
                    .background(LightGreen)
                    .border(1.dp, LightGreen)
                    .height(57.dp)
                    .weight(1f).clickable { navHost.navigate("Profile") }) {
                    Icon(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "",
                        tint = LightBrown,
                        modifier = Modifier
                            .align(Alignment.Center)
                        //.padding(end = 21.dp, bottom = 25.dp)
                    )
                }
            }
        }
    }
}