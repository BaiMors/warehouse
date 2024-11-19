package com.example.warehouse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.warehouse.R
import com.example.warehouse.models.Gallery
import com.example.warehouse.models.Works
import com.example.warehouse.service.Constants
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.ui.theme.LightGreen
import com.example.warehouse.view_models.MainViewModel
import com.example.warehouse.view_models.ProfileVM
import io.github.jan.supabase.auth.auth

@Composable
fun MyOpen(navHost: NavHostController, viewModel: MainViewModel){
    val mw by viewModel.worksList.collectAsState()
    val myWorks = mw.filter { work ->
        work.author == Constants.supabase.auth.currentUserOrNull()!!.id
    }
    println(mw.size)
    println(myWorks.size)
    Column(
        modifier = Modifier
            .zIndex(1f)
    ) {
        Row {
            Box {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "",
                    tint = LightBrown,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 21.dp, end = 21.dp, bottom = 25.dp, top = 45.dp)
                        .clickable { navHost.navigate("Profile") }
                )
            }
            Text(
                text = "Мои работы",
                fontSize = 20.sp,
                color = Brown,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(top = 45.dp, bottom = 25.dp)
            )
        }
    }

    val isDataLoaded by viewModel.isDataLoaded.collectAsState()
    if (!isDataLoaded) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGreen),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = LightBrown)
        }
    } else {
        MyOpenContent(navHost, myWorks)
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
fun MyOpenContent(navHost: NavHostController, myWorks: List<Works>){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkGreen)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 100.dp)
        ) {
            LazyColumn(
                Modifier
                    .fillMaxHeight()
                    .padding(bottom = 60.dp)
            ) {
                items(myWorks) { work ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .background(LightGreen, shape = RoundedCornerShape(5.dp))
                                //.width(370.dp)
                                .border(15.dp, DarkGreen)
                                .align(Alignment.Center)
                        ) {
                            Row(
                                modifier = Modifier
                                    //.width(355.dp)
                                    .padding(start = 14.dp, end = 14.dp, top = 10.dp)
                            ) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Box(modifier = Modifier
                                        .width(270.dp)
                                        .clickable { navHost.navigate("ReadWork/${work.id}/${work.chapters!![0].id}") }) {
                                        Text(
                                            text = work.name,
                                            fontSize = 19.sp,
                                            softWrap = true,
                                            lineHeight = 21.sp,
                                            textDecoration = TextDecoration.Underline,
                                            color = LightBrown,
                                            textAlign = TextAlign.Left,
                                            modifier = Modifier
                                                .padding(start = 14.dp, top = 15.dp, bottom = 13.dp)
                                                .align(Alignment.CenterStart)
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.author),
                                        contentDescription = "",
                                        tint = Brown,
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(end = 12.dp, top = 15.dp)
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    //.height(260.dp)
                                    //.width(345.dp)
                                    .fillMaxWidth()
                                    //.border(50.dp, LightGreen)
                                    .padding(start = 20.dp, end = 20.dp, top = 15.dp)
                                    .background(DarkGreen)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                val galleryList: List<Gallery> = work.gallery ?: emptyList()
                                LazyRow(modifier = Modifier.padding(start = 10.dp, top = 20.dp)) {
                                    items(galleryList) { image ->
                                        if (!image.image.contains("Warehouse")) {
                                            AsyncImage(
                                                model = image.image,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp)
                                                    .width(150.dp)
                                                    .border(1.dp, Brown),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            AsyncImage(
                                                model = image.image,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp)
                                                    .width(150.dp)
                                                    .border(1.dp, Brown),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .padding(start = 10.dp, top = 20.dp)
                                        .background(Color.Black)
                                        .border(1.dp, Brown)
                                ) {

                                }
                                Column(
                                    modifier = Modifier.padding(end = 13.dp)
                                ) {
                                    Row(modifier = Modifier.padding(top = 15.dp, bottom = 5.dp)) {
                                        Box {
                                            Icon(
                                                painter = painterResource(R.drawable.fandom),
                                                contentDescription = "",
                                                tint = Brown,
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart)
                                            )
                                            work.fandoms?.map { it.fandom1?.name }?.let {
                                                Text(
                                                    text = it.joinToString(", "),
                                                    fontSize = 12.sp,
                                                    color = LightBrown,
                                                    modifier = Modifier
                                                        .align(CenterEnd)
                                                        .padding(start = 30.dp),
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }
                                    }
                                    Row(modifier = Modifier.padding(bottom = 5.dp)) {
                                        Box {
                                            Icon(
                                                painter = painterResource(R.drawable.author),
                                                contentDescription = "",
                                                tint = Brown,
                                                modifier = Modifier
                                                //.align(Alignment.CenterStart)
                                            )
                                            work.author1?.let {
                                                Text(
                                                    text = it.name,
                                                    fontSize = 12.sp,
                                                    color = LightBrown,
                                                    modifier = Modifier
                                                        .align(CenterEnd)
                                                        .padding(start = 30.dp),
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }

                                    }
                                    HorizontalDivider(
                                        thickness = 1.dp,
                                        color = Brown.copy(alpha = 0.5f),
                                        modifier = Modifier.padding(top = 5.dp, bottom = 7.dp)
                                    )
                                    Row(modifier = Modifier.padding(bottom = 5.dp)) {
                                        Box {
                                            Icon(
                                                painter = painterResource(R.drawable.genre),
                                                contentDescription = "",
                                                tint = Brown,
                                                modifier = Modifier
                                                //.align(Alignment.CenterStart)
                                            )
                                            work.tags?.map { it.tag1?.name }?.let {
                                                Text(
                                                    text = it.joinToString(", "),
                                                    fontSize = 12.sp,
                                                    color = LightBrown,
                                                    modifier = Modifier
                                                        .align(CenterEnd)
                                                        .padding(start = 30.dp),
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }

                                    }
                                    HorizontalDivider(
                                        thickness = 1.dp,
                                        color = Brown.copy(alpha = 0.5f),
                                        modifier = Modifier.padding(top = 5.dp, bottom = 7.dp)
                                    )
                                    Row(modifier = Modifier.padding(bottom = 5.dp)) {
                                        Box {
                                            Icon(
                                                painter = painterResource(R.drawable.status),
                                                contentDescription = "",
                                                tint = Brown,
                                                modifier = Modifier
                                                //.align(Alignment.CenterStart)
                                            )
                                            Text(
                                                text = work.status,
                                                fontSize = 12.sp,
                                                color = LightBrown,
                                                modifier = Modifier
                                                    .align(CenterEnd)
                                                    .padding(start = 30.dp),
                                                textAlign = TextAlign.Start
                                            )
                                        }

                                    }
                                    Row(modifier = Modifier.padding(bottom = 5.dp)) {
                                        Box {
                                            Icon(
                                                painter = painterResource(R.drawable.date),
                                                contentDescription = "",
                                                tint = Brown,
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart)
                                            )
                                            Text(
                                                text = work.date.removeRange(
                                                    10..work.date.length - 1 // range
                                                ), fontSize = 12.sp, color = LightBrown,
                                                modifier = Modifier
                                                    .align(CenterEnd)
                                                    .padding(start = 30.dp),
                                                textAlign = TextAlign.Start
                                            )
                                        }

                                    }
                                    Row(modifier = Modifier.padding(bottom = 5.dp)) {
                                        Box {
                                            Icon(
                                                painter = painterResource(R.drawable.chapters),
                                                contentDescription = "",
                                                tint = Brown,
                                                modifier = Modifier
                                                //.align(Alignment.CenterStart)
                                            )
                                            Text(
                                                text = work.num_chapters.toString() + " глав(а)",
                                                fontSize = 12.sp, color = LightBrown,
                                                modifier = Modifier
                                                    .align(CenterEnd)
                                                    .padding(start = 30.dp),
                                                textAlign = TextAlign.Start
                                            )
                                        }

                                    }
                                    Row(modifier = Modifier.padding(bottom = 5.dp)) {
                                        Box {
                                            Icon(
                                                painter = painterResource(R.drawable.likes),
                                                contentDescription = "",
                                                tint = Brown,
                                                modifier = Modifier
                                                //.align(Alignment.CenterStart)
                                            )
                                            Text(
                                                text = work.likes.toString(),
                                                fontSize = 12.sp,
                                                color = LightBrown,
                                                modifier = Modifier
                                                    .align(CenterEnd)
                                                    .padding(start = 30.dp),
                                                textAlign = TextAlign.Start
                                            )
                                        }

                                    }
                                }
                            }
                            Box {
                                Text(
                                    text = work.description,
                                    fontSize = 14.sp,
                                    color = LightBrown,
                                    softWrap = true,
                                    lineHeight = 20.sp,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier
                                        .padding(
                                            start = 24.dp,
                                            top = 17.dp,
                                            bottom = 22.dp,
                                            end = 24.dp
                                        )
                                        .align(Alignment.CenterStart)
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .background(DarkGreen)
                                    .padding(end = 7.dp)
                                    .height(1.dp)
                                    .width(329.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}