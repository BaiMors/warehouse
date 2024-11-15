package com.example.warehouse.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import com.example.warehouse.R
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.ui.theme.LightGreen
import com.example.warehouse.view_models.CatalogueVM
import com.example.warehouse.view_models.MainPageViewModel

@Composable
fun Catalogue(navHost: NavHostController, viewModel: CatalogueVM) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkGreen)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(bottom = 60.dp).verticalScroll(rememberScrollState())) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 21.dp, end = 21.dp, top = 17.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { navHost.navigate("CatalogueOpen/${"Работы"}") }
                ){
                    Image(
                        painter = painterResource(id = R.drawable.imworks),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth()
                            .height(190.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().height(190.dp)
                            .background(Color.Black.copy(alpha = 0.85f)).clip(RoundedCornerShape(5.dp))
                    )
                    Row {
                        Box(modifier = Modifier.fillMaxWidth().height(190.dp)) {
                            Icon(
                                painter = painterResource(R.drawable.catworks),
                                contentDescription = "",
                                tint = Brown,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(15.dp)
                            )
                            Text(
                                text = "Работы",
                                modifier = Modifier
                                    .padding(start = 50.dp, bottom = 17.dp)
                                    .align(Alignment.BottomStart),
                                color = Color(0xFFfefae0),
                                fontSize = 19.sp
                            )
                            Icon(
                                painter = painterResource(R.drawable.forward),
                                contentDescription = "",
                                tint = LightBrown,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(15.dp)
                            )
                        }

                    }



                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 21.dp, end = 21.dp, top = 17.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { navHost.navigate("CatalogueOpen/${"Фандомы"}") }
                ){
                    Image(
                        painter = painterResource(id = R.drawable.imfandoms),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth()
                            .height(190.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().height(190.dp)
                            .background(Color.Black.copy(alpha = 0.85f)).clip(RoundedCornerShape(5.dp))
                    )
                    Row {
                        Box(modifier = Modifier.fillMaxWidth().height(190.dp)) {
                            Icon(
                                painter = painterResource(R.drawable.catfandoms),
                                contentDescription = "",
                                tint = Brown,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(15.dp)
                            )
                            Text(
                                text = "Фандомы",
                                modifier = Modifier
                                    .padding(start = 50.dp, bottom = 17.dp)
                                    .align(Alignment.BottomStart),
                                color = Color(0xFFfefae0),
                                fontSize = 19.sp
                            )
                            Icon(
                                painter = painterResource(R.drawable.forward),
                                contentDescription = "",
                                tint = LightBrown,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(15.dp)
                            )
                        }

                    }



                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 21.dp, end = 21.dp, top = 17.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { navHost.navigate("CatalogueOpen/${"Тэги"}") }
                ){
                    Image(
                        painter = painterResource(id = R.drawable.imtags),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth()
                            .height(190.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().height(190.dp)
                            .background(Color.Black.copy(alpha = 0.85f)).clip(RoundedCornerShape(5.dp))
                    )
                    Row {
                        Box(modifier = Modifier.fillMaxWidth().height(190.dp)) {
                            Icon(
                                painter = painterResource(R.drawable.cattags),
                                contentDescription = "",
                                tint = Brown,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(15.dp)
                            )
                            Text(
                                text = "Тэги",
                                modifier = Modifier
                                    .padding(start = 50.dp, bottom = 17.dp)
                                    .align(Alignment.BottomStart),
                                color = Color(0xFFfefae0),
                                fontSize = 19.sp
                            )
                            Icon(
                                painter = painterResource(R.drawable.forward),
                                contentDescription = "",
                                tint = LightBrown,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(15.dp)
                            )
                        }

                    }



                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 21.dp, end = 21.dp, top = 17.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { navHost.navigate("CatalogueOpen/${"Авторы"}") }
                ){
                    Image(
                        painter = painterResource(id = R.drawable.imauthors),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth()
                            .height(190.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().height(190.dp)
                            .background(Color.Black.copy(alpha = 0.85f)).clip(RoundedCornerShape(5.dp))
                    )
                    Row {
                        Box(modifier = Modifier.fillMaxWidth().height(190.dp)) {
                            Icon(
                                painter = painterResource(R.drawable.catauthors),
                                contentDescription = "",
                                tint = Brown,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(15.dp)
                            )
                            Text(
                                text = "Авторы",
                                modifier = Modifier
                                    .padding(start = 50.dp, bottom = 17.dp)
                                    .align(Alignment.BottomStart),
                                color = Color(0xFFfefae0),
                                fontSize = 19.sp
                            )
                            Icon(
                                painter = painterResource(R.drawable.forward),
                                contentDescription = "",
                                tint = LightBrown,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(15.dp)
                            )
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