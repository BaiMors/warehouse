package com.example.warehouse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.warehouse.R
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.view_models.CreateEditWorkVM
import com.example.warehouse.view_models.MainViewModel

@Composable
fun CreateEditWork(navHost: NavHostController, viewModel: CreateEditWorkVM, work: String?){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkGreen)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(top = 45.dp)) {
            Icon(
                painter = painterResource(R.drawable.back),
                contentDescription = "",
                tint = LightBrown,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 21.dp, top = 15.dp)
                    .clickable {
                        navHost.navigate("MyOpen")
                    }
            )
            Icon(
                painter = painterResource(R.drawable.save_work),
                contentDescription = "",
                tint = LightBrown,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 21.dp, top = 15.dp)
                    .clickable { navHost.navigate("UpdateProfile") }
            )
        }
        val isDataLoaded by viewModel.isDataLoaded.collectAsState()
        if (isDataLoaded == false)
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
            CreateEditWorkData(navHost, viewModel, work)
        }
    }


}

@Composable
fun CreateEditWorkData(navHost: NavHostController, viewModel: CreateEditWorkVM, work: String?){
    val sw = viewModel.worksList.collectAsState()
    val selectedWork = sw.value.first { it.id == work }
    var name by remember {
        mutableStateOf(selectedWork.name)
    }
    var status by remember {
        mutableStateOf(selectedWork.status)
    }
    var listFandoms = viewModel.fandomsList.collectAsState()
    val selectedFandoms by remember {
        mutableStateOf(listOf(selectedWork.fandoms!!.map { formap -> formap.fandom1!!.name }))
    }
    var listTags = viewModel.tagsList.collectAsState()
    var selectedTags by remember {
        mutableStateOf(listOf(selectedWork.tags!!.map { formap -> formap.tag1!!.name }))
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 21.dp, top = 38.dp)) {
            TextField(
                value = name,
                onValueChange = {newText -> name = newText},
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(LightBrown),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightBrown,
                    focusedContainerColor = LightBrown,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen
                )
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 21.dp)) {
            TextField(
                value = selectedWork.status,
                onValueChange = {newText -> name = newText},
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(LightBrown),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightBrown,
                    focusedContainerColor = LightBrown,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen
                )
            )
            Column {
                LazyRow {
                    items(selectedTags){ item ->
                        Row {
                            Text(text = item.toString(), modifier = Modifier.padding(top = 40.dp), color = DarkGreen)

                        }
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.border(1.dp, Brown.copy(0.5f)))
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(LightBrown)
                .padding(start = 21.dp, end = 21.dp, top = 60.dp)) {
                Text(text = "Статус", color = Brown, modifier = Modifier)
                Icon(painter = painterResource(R.drawable.arrow), contentDescription = "Статус", tint = Brown, modifier = Modifier)
            }
        }
    }
}