package com.example.warehouse.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.warehouse.R
import com.example.warehouse.models.Users
import com.example.warehouse.service.Constants
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.ui.theme.LightGreen
import com.example.warehouse.view_models.MainPageViewModel
import com.example.warehouse.view_models.MainViewModel
import com.example.warehouse.view_models.ProfileVM
import io.github.jan.supabase.auth.auth

//o@gmail.com
@Composable
fun Profile(navHost: NavHostController, viewModel: ProfileVM, viewModel1: MainViewModel){
    val ul = viewModel.userList.collectAsState()
    val userEmail = MainViewModel.PrefsHelper.getSharedPreferences().getString("user_email", null)
    val curUsAuth = Constants.supabase.auth.currentUserOrNull()
    val curUsPublic = ul.value.find { it.id == curUsAuth?.id }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkGreen)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(top = 45.dp)) {
            Icon(
                painter = painterResource(R.drawable.profile_out),
                contentDescription = "",
                tint = Brown,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 21.dp, top = 15.dp)
                    .clickable {
                        navHost.navigate("Avtorization")
                        val editor = MainViewModel.PrefsHelper
                            .getSharedPreferences()
                            .edit()
                        editor
                            .clear()
                            .apply()
                    }
            )
            Icon(
                painter = painterResource(R.drawable.profile_edit),
                contentDescription = "",
                tint = Brown,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 21.dp, top = 15.dp)
                    .clickable { navHost.navigate("UpdateProfile") }
            )
        }
        val isUserLoaded by viewModel.isUserLoaded.collectAsState()
        val isDataLoaded by viewModel1.isDataLoaded.collectAsState()
        println("isUserLoaded::::: "+isUserLoaded)
        println("isDataLoaded::::: "+isDataLoaded)
        if (isUserLoaded == false || isDataLoaded == false)
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGreen),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = LightBrown)
            }
        }
        else{
            println("зашли в отрисовку isUserLoaded::::: "+isUserLoaded)
            println("зашли в отрисовку isDataLoaded::::: "+isDataLoaded)
            ProfileData(curUsPublic, navHost)
        }
    }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                .weight(1f)
                .clickable { navHost.navigate("MainPage") }) {
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
                .background(LightGreen)
                .border(1.dp, LightGreen)
                .height(57.dp)
                .weight(1f)
                .clickable { navHost.navigate("Search") }) {
                Icon(
                    painter = painterResource(R.drawable.search),
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
                .weight(1f)
                .clickable { navHost.navigate("Catalogue") }) {
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
                .background(DarkGreen)
                .border(2.dp, LightGreen)
                .height(57.dp)
                .weight(1f)
                .clickable { navHost.navigate("Profile") }) {
                Icon(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "",
                    tint = Brown,
                    modifier = Modifier
                        .align(Alignment.Center)
                    //.padding(end = 21.dp, bottom = 25.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileData(curUsPublic: Users?, navHost: NavHostController){
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(25.dp))
        Box(modifier = Modifier.fillMaxWidth()){
            if (curUsPublic?.image.isNullOrEmpty())
            {
                Canvas(
                    Modifier
                        .size(128.dp)
                        .background(Color.Transparent)
                        .align(Alignment.Center)) {
                    drawCircle(
                        color = LightBrown,
                        center = center,
                        //radius = 128.dp.to
                    )
                }
            }
            else{
                AsyncImage(
                    model = curUsPublic?.image,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(128.dp)
                        .width(128.dp).clip(RoundedCornerShape(100)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Text(
            text = curUsPublic!!.name,
            fontSize = 24.sp,
            color = LightBrown,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 31.dp)
        )
        HorizontalDivider(thickness = 1.dp, color = Brown.copy(alpha = 0.5f),
            modifier = Modifier.padding(top = 13.dp, start = 21.dp, end = 21.dp))
        Text(
            text = curUsPublic!!.username,
            fontSize = 16.sp,
            color = LightBrown,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 11.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier
            .background(LightGreen, RoundedCornerShape(25.dp))
            .align(Alignment.CenterHorizontally)
            .clip(RoundedCornerShape(25.dp))) {
            Text(
                text = if (curUsPublic!!.title.isNullOrEmpty()) "Пока нет титула"
                else curUsPublic!!.title.toString(),
                fontSize = 16.sp,
                color = Brown,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(7.dp)
            )
        }
        Text(
            text = if (curUsPublic!!.description.isNullOrEmpty()) "Пока нет описания"
            else curUsPublic!!.description.toString(),
            fontSize = 14.sp,
            color = LightBrown,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 38.dp)
        )
        Spacer(modifier = Modifier.height(70.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 21.dp, top = 20.dp)
            .clip(
                RoundedCornerShape(5)
            )
            .clickable { navHost.navigate("FavotitesOpen") }){
            Box(modifier = Modifier
                .background(LightGreen, RoundedCornerShape(5.dp))
                .fillMaxWidth()) {
                Icon(
                    painter = painterResource(R.drawable.fav_work),
                    contentDescription = "",
                    tint = LightBrown,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
                Text(
                    text = "Избранные работы",
                    fontSize = 16.sp,
                    color = LightBrown,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 42.dp, top = 23.dp, bottom = 23.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.forward),
                    contentDescription = "",
                    tint = LightBrown,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 13.dp)
                )
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 21.dp, top = 20.dp)
            .clip(
                RoundedCornerShape(5)
            )
            .clickable { navHost.navigate("MyOpen") }){
            Box(modifier = Modifier
                .background(LightGreen, RoundedCornerShape(5.dp))
                .fillMaxWidth()) {
                Icon(
                    painter = painterResource(R.drawable.author),
                    contentDescription = "",
                    tint = LightBrown,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
                Text(
                    text = "Мои работы",
                    fontSize = 16.sp,
                    color = LightBrown,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 42.dp, top = 23.dp, bottom = 23.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.forward),
                    contentDescription = "",
                    tint = LightBrown,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 13.dp)
                )
            }
        }
    }
}