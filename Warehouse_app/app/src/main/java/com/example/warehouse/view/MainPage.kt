package com.example.warehouse.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment.Companion.Rectangle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.warehouse.R
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.ui.theme.LightGreen
import com.example.warehouse.view_models.AvtorizationVM
import com.example.warehouse.view_models.MainPageViewModel

//@Preview()
@Composable
//fun MainPage() {
fun MainPage(navHost: NavHostController, viewModel: MainPageViewModel) {
    //val ctx = LocalContext.current
    val worksList by viewModel.worksList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkGreen)
    ) {
        Text(
            text = "Добро пожаловать на Склад",
            fontSize = 22.sp,
            color = LightBrown,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(start = 21.dp, top = 13.dp, bottom = 25.dp,)
        )
        Box(
            modifier = Modifier
                .background(Brown)
                .padding(start = 21.dp)
                .height(1.dp)
                .width(329.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Вот что популярно сегодня",
                    fontSize = 20.sp,
                    color = LightBrown,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(start = 21.dp, top = 17.dp, bottom = 25.dp,)
                        .align(Alignment.CenterStart)
                )
                Icon(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = "",
                    tint = Brown,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 21.dp)
                )
            }
        }
        LazyColumn {
            items(worksList) { work ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .background(LightGreen, shape = RoundedCornerShape(5.dp))
                            //.width(370.dp)
                            .padding(start = 21.dp, end = 21.dp)
                            .align(Alignment.Center)
                    ) {
                        Row(
                            modifier = Modifier
                                //.width(355.dp)
                                .padding(start = 14.dp, end = 14.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Box(modifier = Modifier.width(280.dp)) {
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
                                .height(260.dp)
                                .width(345.dp)
                                .background(DarkGreen)
                                .align(Alignment.CenterHorizontally)
                        ) {

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
                                    .padding(start = 14.dp, top = 17.dp, bottom = 7.dp, end = 14.dp)
                                    .align(Alignment.CenterStart)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(DarkGreen)
                                .padding(end = 12.dp)
                                .height(1.dp)
                                .width(329.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
            /*        Text(
            text = "Добро пожаловать на Склад",
            fontSize = 22.sp,
            color = LightBrown,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(start = 21.dp, top = 13.dp, bottom = 25.dp,)
        )
        Box(modifier = Modifier
            .background(Brown)
            .padding(start = 21.dp)
            .height(1.dp)
            .width(329.dp)
            .align(Alignment.CenterHorizontally))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Вот что популярно сегодня",
                    fontSize = 20.sp,
                    color = LightBrown,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(start = 21.dp, top = 17.dp, bottom = 25.dp,)
                        .align(Alignment.CenterStart)
                )
                Icon(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = "",
                    tint = Brown,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 21.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .background(LightGreen, shape = RoundedCornerShape(5.dp))
                .width(355.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Row(modifier = Modifier
                .width(355.dp)) {
                Box(modifier = Modifier.fillMaxWidth()){
                    Box(modifier = Modifier.width(280.dp)) {
                        Text(
                            text = "Название работы должно быть привязано и помещено здесь",
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
                    .height(260.dp)
                    .width(345.dp)
                    .background(DarkGreen)
                    .align(Alignment.CenterHorizontally)
            ) {

            }
            Box {
                Text(
                    text = "Consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. ",
                    fontSize = 14.sp,
                    color = LightBrown,
                    softWrap = true,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(start = 14.dp, top = 17.dp, bottom = 7.dp, end = 14.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }*/
        }
        /*    LazyColumn {
        items(worksList) { work ->
            work.author1?.let { Text(text = it.name, color = Color.Black) }
        }
    }*/
    }
}