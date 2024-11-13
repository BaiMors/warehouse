package com.example.warehouse.view

import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.warehouse.R
import com.example.warehouse.models.Chapters
import com.example.warehouse.models.Gallery
import com.example.warehouse.models.Works
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.ui.theme.LightGreen
import com.example.warehouse.view_models.MainViewModel

@Composable
fun ReadWork(
    navHost: NavHostController,
    viewModel: MainViewModel,
    work: String?,
    chapter: String?
) {
    val _selectedWork = viewModel.worksList.collectAsState()
    val isDataLoaded by viewModel.isDataLoaded.collectAsState()

    println("work = "+work)
    println("sizeof _selw "+_selectedWork.value.size)
    //println("it.id = "+_selectedWork.value.filter { it.id == "18922428-fc78-41af-b596-a48da5088dea" }.first().id)
    if (!isDataLoaded)
    {
        println("кажется мы зашли в загрузку")
        Box(
            modifier = Modifier.fillMaxSize().background(DarkGreen),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = LightBrown)
        }
    }
    else if (viewModel.worksList.collectAsState().value.isEmpty()){
        println("кажется мы зашли в загрузку")
        Box(
            modifier = Modifier.fillMaxSize().background(DarkGreen),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = LightBrown)
        }
    }
    else{
        println("отрисовка")
        val SelectedWork: Works = _selectedWork.value.first{ it.id == work}
        ReadSelectedWork(navHost, SelectedWork, chapter)
    }

    //println(SelectedWork.name + " " + SelectedWork.author1!!.name + " " + SelectedWork.chapters!![0].name)

    //val SelectedWork: Works = _selectedWork.value.first{ it.id == work}
    //println(SelectedWork.name)

}

@Composable
fun ReadSelectedWork(navHost: NavHostController, SelectedWork: Works, chapter: String?){
    Box(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.align(Alignment.TopCenter).zIndex(1f)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "",
                    tint = LightBrown,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 21.dp, end = 21.dp, bottom = 25.dp, top = 45.dp)
                        .clickable { navHost.navigateUp() }
                )//сн
                Icon(
                    painter = painterResource(R.drawable.outlined),
                    contentDescription = "",
                    tint = Brown,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp, top = 15.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(DarkGreen)
                .padding(top = 80.dp)
                .verticalScroll(rememberScrollState())
        ) {


            Box(modifier = Modifier.fillMaxWidth()) {
                val galleryList: List<Gallery> = SelectedWork.gallery ?: emptyList()
                LazyRow(modifier = Modifier.padding(start = 10.dp, top = 20.dp).align(Alignment.Center)) {
                    items(galleryList) { image ->
                        AsyncImage(
                            model = image.image,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .width(320.dp)
                                .border(1.dp, Brown)
                                .clip(RoundedCornerShape(5.dp))
                                .align(Alignment.Center),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = SelectedWork.name,
                    fontSize = 21.sp,
                    color = LightBrown,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 17.dp, bottom = 17.dp, start = 50.dp, end = 50.dp)
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Brown,
                modifier = Modifier
                    .padding(start = 72.dp, end = 72.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Column(
                modifier = Modifier.padding(end = 13.dp)
            ) {
                Row(modifier = Modifier.padding(top = 22.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.fandom),
                            contentDescription = "",
                            tint = Brown,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Text(
                            text = SelectedWork.fandoms!!.map { it.fandom1!!.name }.joinToString(", "), fontSize = 16.sp, color = LightBrown,
                            modifier = Modifier
                                .align(CenterStart)
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Start
                        )
                    }
                }
                Row(modifier = Modifier.padding(top = 22.dp, bottom = 5.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.author),
                            contentDescription = "",
                            tint = Brown,
                            modifier = Modifier
                            //.align(Alignment.CenterStart)
                        )
                        Text(
                            text = SelectedWork.author1!!.name, fontSize = 16.sp, color = LightBrown,
                            modifier = Modifier
                                .align(CenterStart)
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Start
                        )
                    }

                }
                HorizontalDivider(
                    thickness = 1.dp, color = Brown,
                    modifier = Modifier
                        .padding(start = 21.dp, end = 21.dp, top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row(modifier = Modifier.padding(top = 22.dp, bottom = 5.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.genre),
                            contentDescription = "",
                            tint = Brown,
                            modifier = Modifier
                            //.align(Alignment.CenterStart)
                        )
                        Text(
                            text = SelectedWork.tags!!.map { it.tag1!!.name }.joinToString(", "), fontSize = 16.sp, color = LightBrown,
                            modifier = Modifier
                                .align(CenterStart)
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Start
                        )
                    }

                }
                HorizontalDivider(
                    thickness = 1.dp, color = Brown,
                    modifier = Modifier
                        .padding(start = 21.dp, end = 21.dp, top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row(modifier = Modifier.padding(top = 22.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.status),
                            contentDescription = "",
                            tint = Brown,
                            modifier = Modifier
                            //.align(Alignment.CenterStart)
                        )
                        Text(
                            text = SelectedWork.status, fontSize = 16.sp, color = LightBrown,
                            modifier = Modifier
                                .align(CenterStart)
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Start
                        )
                    }

                }
                Row(modifier = Modifier.padding(top = 22.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.date),
                            contentDescription = "",
                            tint = Brown,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Text(
                            text = SelectedWork.date.removeRange(
                                10..SelectedWork.date.length - 1 // range
                            ), fontSize = 16.sp, color = LightBrown,
                            modifier = Modifier
                                .align(CenterStart)
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Start
                        )
                    }

                }
                Row(modifier = Modifier.padding(top = 22.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.chapters),
                            contentDescription = "",
                            tint = Brown,
                            modifier = Modifier
                            //.align(Alignment.CenterStart)
                        )
                        Text(
                            text = SelectedWork.num_chapters.toString(),
                            fontSize = 16.sp, color = LightBrown,
                            modifier = Modifier
                                .align(CenterStart)
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Start
                        )
                    }

                }
                HorizontalDivider(
                    thickness = 1.dp, color = Brown,
                    modifier = Modifier
                        .padding(start = 21.dp, end = 21.dp, top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row(modifier = Modifier.padding(top = 22.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.likes),
                            contentDescription = "",
                            tint = Brown,
                            modifier = Modifier
                            //.align(Alignment.CenterStart)
                        )
                        Text(
                            text = SelectedWork.likes.toString(), fontSize = 16.sp, color = LightBrown,
                            modifier = Modifier
                                .align(CenterStart)
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Start
                        )
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 21.dp, end = 21.dp, top = 22.dp)
                ) {
                    Text(
                        text = SelectedWork.description,
                        fontSize = 14.sp,
                        color = LightBrown,
                        textAlign = TextAlign.Justify,
                        lineHeight = 20.sp
                    )
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Brown,
                    modifier = Modifier
                        .padding(start = 72.dp, end = 72.dp, top = 34.dp, bottom = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 25.dp, end = 25.dp, bottom = 32.dp)
                ) {
                    Text(
                        text = SelectedWork.chapters!!.firstOrNull{ it.id == chapter}!!.name,
                        fontSize = 24.sp,
                        color = LightBrown,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 25.dp, end = 25.dp, bottom = 30.dp)
                ) {
                    Text(
                        text = SelectedWork.chapters!!.firstOrNull{ it.id == chapter}!!.content,
                        fontSize = 15.sp,
                        color = LightBrown,
                        textAlign = TextAlign.Justify
                        //lineHeight = 20.sp
                    )
                }
                val forChapters: List<Chapters> = SelectedWork.chapters ?: emptyList()
                Box {
                    LazyRow(modifier = Modifier
                        .height(57.dp)
                        .background(LightGreen)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        items(forChapters){ chapt ->
                            Box {
                                Text(
                                    text = chapt.name,
                                    fontSize = 16.sp,
                                    color = LightBrown,
                                    modifier = Modifier.background(DarkGreen)
                                        .border(2.dp, LightGreen)
                                        .padding(10.dp)
                                        .align(Center)
                                        .clickable { navHost.navigate("ReadWork/${SelectedWork.id}/${chapt.id}") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
