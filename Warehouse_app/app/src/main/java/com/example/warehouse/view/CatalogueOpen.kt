package com.example.warehouse.view

import android.nfc.Tag
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.warehouse.R
import com.example.warehouse.models.Fandoms
import com.example.warehouse.models.Gallery
import com.example.warehouse.models.Tags
import com.example.warehouse.models.Users
import com.example.warehouse.models.Works
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.ui.theme.LightGreen
import com.example.warehouse.view_models.CatalogueVM

@Composable
fun CatalogueOpen(navHost: NavHostController, viewModel: CatalogueVM, category: String?) {
    val resultList: List<Any> =
        if (category == "Работы") viewModel.ListWorks.collectAsState().value
        else if (category == "Фандомы") viewModel.ListFandoms.collectAsState().value
        else if (category == "Тэги") viewModel.ListTags.collectAsState().value
        else if (category == "Авторы") viewModel.ListAuthors.collectAsState().value
        else emptyList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkGreen)
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
                        .clickable { navHost.navigate("Catalogue") }
                )
            }
            Text(
                text = "$category",
                fontSize = 20.sp,
                color = Brown,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(top = 45.dp, bottom = 25.dp)
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            LazyColumn {
                items(resultList) { item ->
                    when (item) {
                        is Works -> {
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
                                            Box(modifier = Modifier.width(270.dp).clickable { navHost.navigate("ReadWork/${item.id}/${item.chapters!![0].id}") }) {
                                                Text(
                                                    text = item.name,
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
                                                painter = painterResource(R.drawable.outlined),
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
                                        val galleryList: List<Gallery> = item.gallery ?: emptyList()
                                        LazyRow(modifier = Modifier.padding(start = 10.dp, top = 20.dp)) {
                                            items(galleryList) { image ->
                                                //Text(text = image.image)
                                                if (!image.image.contains("Warehouse")) {
                                                    /*val imageState = rememberAsyncImagePainter(
                                                        model = ImageRequest.Builder(LocalContext.current)
                                                            .data(image.image)
                                                            .size(Size.ORIGINAL).build()
                                                    ).state
                                                    if (imageState is AsyncImagePainter.State.Error) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .height(200.dp),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            CircularProgressIndicator()
                                                        }
                                                    }
                                                    if (imageState is AsyncImagePainter.State.Success) {
                                                        Image(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .height(200.dp)
                                                                .border(1.dp, Brown),
                                                            painter = imageState.painter,
                                                            contentDescription = "",
                                                            contentScale = ContentScale.Crop,
                                                        )
                                                    }*/
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
                                                    item.fandoms?.map { it.fandom1?.name }?.let {
                                                        Text(
                                                            text = it.joinToString(", "), fontSize = 12.sp, color = LightBrown,
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
                                                    item.author1?.let {
                                                        Text(
                                                            text = it.name, fontSize = 12.sp, color = LightBrown,
                                                            modifier = Modifier
                                                                .align(CenterEnd)
                                                                .padding(start = 30.dp),
                                                            textAlign = TextAlign.Start
                                                        )
                                                    }
                                                }

                                            }
                                            HorizontalDivider(thickness = 1.dp, color = Brown.copy(alpha = 0.5f), modifier = Modifier.padding(top = 5.dp, bottom = 7.dp))
                                            Row(modifier = Modifier.padding(bottom = 5.dp)) {
                                                Box {
                                                    Icon(
                                                        painter = painterResource(R.drawable.genre),
                                                        contentDescription = "",
                                                        tint = Brown,
                                                        modifier = Modifier
                                                        //.align(Alignment.CenterStart)
                                                    )
                                                    item.tags?.map { it.tag1?.name }?.let {
                                                        Text(
                                                            text = it.joinToString(", "), fontSize = 12.sp, color = LightBrown,
                                                            modifier = Modifier
                                                                .align(CenterEnd)
                                                                .padding(start = 30.dp),
                                                            textAlign = TextAlign.Start
                                                        )
                                                    }
                                                }

                                            }
                                            HorizontalDivider(thickness = 1.dp, color = Brown.copy(alpha = 0.5f), modifier = Modifier.padding(top = 5.dp, bottom = 7.dp))
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
                                                        text = item.status, fontSize = 12.sp, color = LightBrown,
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
                                                        text = item.date.removeRange(
                                                            10..item.date.length - 1 // range
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
                                                        text = item.num_chapters.toString() + " глав(а)",
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
                                                        text = item.likes.toString(), fontSize = 12.sp, color = LightBrown,
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
                                            text = item.description,
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

                        is Fandoms -> {
                            Text(
                                text = item.name,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 20.dp, start = 30.dp)
                                    .clickable { navHost.navigate("CategoryOpen/${"Фандомы"}/${item.name}") },
                                color = LightBrown)
                            HorizontalDivider(thickness = 1.dp, color = Brown, modifier = Modifier.padding(start = 21.dp, end = 21.dp, top = 20.dp))
                        }

                        is Tags -> {
                            Text(
                                text = item.name,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 20.dp, start = 30.dp)
                                    .clickable { navHost.navigate("CategoryOpen/${"Тэги"}/${item.name}") },
                                color = LightBrown)
                            HorizontalDivider(thickness = 1.dp, color = Brown, modifier = Modifier.padding(start = 21.dp, end = 21.dp, top = 20.dp))
                        }

                        is Users -> {
                            Text(
                                text = item.name,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 20.dp, start = 30.dp)
                                    .clickable { navHost.navigate("CategoryOpen/${"Авторы"}/${item.name}") },
                                color = LightBrown)
                            HorizontalDivider(thickness = 1.dp, color = Brown, modifier = Modifier.padding(start = 21.dp, end = 21.dp, top = 20.dp))
                        }
                    }

                }
            }
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
                    .background(DarkGreen)
                    .border(2.dp, LightGreen)
                    .height(57.dp)
                    .weight(1f)
                    .clickable { navHost.navigate("Catalogue") }) {
                    Icon(
                        painter = painterResource(R.drawable.catalogue),
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
                    .weight(1f)
                    .clickable { navHost.navigate("Profile") }) {
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