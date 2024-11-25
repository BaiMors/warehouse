package com.example.warehouse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.warehouse.R
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.ui.theme.LightGreen
import com.example.warehouse.view_models.CreateEditWorkVM
import com.example.warehouse.view_models.MainViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateEditWork(navHost: NavHostController, viewModel: CreateEditWorkVM, work: String?) {
    val isDataLoaded by viewModel.isDataLoaded.collectAsState()
    if (isDataLoaded == false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGreen),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = LightBrown)
        }
    } else {
        val sw = viewModel.worksList.collectAsState()
        val selectedWork = sw.value.first { it.id == work }
        var searchQuery by remember { mutableStateOf("") }
        var searchQuery1 by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var name by remember {
            mutableStateOf(selectedWork.name)
        }
        var desc by remember {
            mutableStateOf(selectedWork.description)
        }
        var status by remember {
            mutableStateOf(selectedWork.status)
        }
        var chapters = remember {
            mutableStateOf(selectedWork.chapters!!.toMutableList())
        }

        val selectedFandoms = remember {
            mutableStateOf(selectedWork.fandoms!!.map { formap -> formap.fandom1!!.name }.toList())
        }
        var lf = viewModel.fandomsList.collectAsState()
        var listFandoms = remember {
            mutableStateOf(lf.value.map { it.name }.filter { it !in selectedFandoms.value })
        }

        var selectedTags = remember {
            mutableStateOf(selectedWork.tags!!.map { formap -> formap.tag1!!.name })
        }
        var lt = viewModel.tagsList.collectAsState()
        var listTags =
            remember { mutableStateOf(lt.value.map { it.name }.filter { it !in selectedTags.value }) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.verticalScroll(rememberScrollState())
                .background(DarkGreen)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 45.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth().clickable {
                    navHost.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "",
                        tint = LightBrown,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 21.dp, top = 15.dp)
                            .clickable {
                                navHost.navigateUp()
                            }
                    )
                }

                Box(modifier = Modifier.fillMaxWidth().clickable { navHost.navigate("MyOpen");
                    viewModel.saveWork(name, desc, status, chapters.value.count(), selectedTags.value, selectedFandoms.value) }) {
                    Icon(
                        painter = painterResource(R.drawable.save_work),
                        contentDescription = "",
                        tint = LightBrown,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 21.dp, top = 15.dp)
                            .clickable { navHost.navigate("MyOpen");
                                viewModel.saveWork(name, desc, status, chapters.value.count(), selectedTags.value, selectedFandoms.value) }
                    )
                }

            }

        }
        //CreateEditWorkData(navHost, viewModel, work)
        LazyColumn(modifier = Modifier.padding(top = 45.dp)) {
            item {
                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 45.dp)
                            .fillMaxWidth()
                        //.verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 38.dp, start = 18.dp, end = 18.dp)
                        ) {
                            TextField(
                                value = name,
                                onValueChange = { newText -> name = newText },
                                shape = RoundedCornerShape(3.dp),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 18.dp, end = 18.dp, top = 17.dp)
                        ) {
                            TextField(
                                value = desc,
                                onValueChange = { newText -> desc = newText },
                                shape = RoundedCornerShape(3.dp),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
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

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 18.dp, end = 18.dp, top = 17.dp)
                        ) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .background(LightBrown)
                                .clip(RoundedCornerShape(3.dp))) {
                                /*LazyRow(modifier = Modifier.background(LightBrown)) {
                                    items(selectedTags.value) { item ->
                                        //Row() {
                                        Text(
                                            text = item,
                                            modifier = Modifier
                                                .padding(start = 12.dp, end = 7.dp, top = 10.dp, bottom = 28.dp)
                                                .clickable {
                                                    selectedTags.value =
                                                        selectedTags.value.filter { it !in item };
                                                    listTags.value = listTags.value + item;
                                                },
                                            color = DarkGreen
                                        )

                                        //}
                                    }
                                }*/
                                FlowRow(
                                    modifier = Modifier.padding(start = 12.dp, end = 7.dp, top = 10.dp, bottom = 28.dp)
                                ) {
                                    selectedFandoms.value.forEach { item ->
                                        Text(
                                            text = item,
                                            color = DarkGreen,
                                            modifier = Modifier
                                                .padding(end = 5.dp, bottom = 5.dp)
                                                .clickable {
                                                    selectedFandoms.value =
                                                        selectedFandoms.value.filter { it !in item };
                                                    listFandoms.value = listFandoms.value + item;
                                                }
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider(thickness = 1.dp, color = Brown.copy(0.5f), modifier = Modifier.padding(start = 33.dp, end = 33.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 18.dp, end = 18.dp)
                        ) {
                            // Поиск
                            TextField(
                                value = searchQuery1,
                                onValueChange = { searchQuery1 = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = LightBrown,
                                    focusedContainerColor = LightBrown,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = DarkGreen,
                                    unfocusedTextColor = DarkGreen
                                ),
                                shape = RoundedCornerShape(0.dp),
                                label = { Text("Фэндомы", color = Brown) },
                                trailingIcon = {
                                    IconButton(onClick = { expanded = !expanded }) {
                                        Icon(
                                            painter = if (expanded) painterResource(R.drawable.arrow) else painterResource(
                                                R.drawable.arrow_up
                                            ),
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 18.dp, end = 18.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                                    .background(LightBrown)
                            ) {
                                items(listFandoms.value.filter {
                                    it.contains(
                                        searchQuery1,
                                        ignoreCase = true
                                    )
                                }) { fd ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = selectedFandoms.value.contains(fd),
                                            onCheckedChange = {
                                                if (it) {
                                                    selectedFandoms.value += fd;
                                                    listFandoms.value = listFandoms.value.filter { it !in fd }
                                                }
                                                else
                                                    selectedFandoms.value = selectedFandoms.value.filter { it !in fd }

                                            }
                                        )
                                        Text(text = fd, color = Brown, fontSize = 16.sp)
                                    }
                                }
                            }
                        }



                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 18.dp, end = 18.dp, top = 17.dp)
                        ) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .background(LightBrown)
                                .clip(RoundedCornerShape(3.dp))) {
                                /*LazyRow(modifier = Modifier.background(LightBrown)) {
                                    items(selectedTags.value) { item ->
                                        //Row() {
                                        Text(
                                            text = item,
                                            modifier = Modifier
                                                .padding(start = 12.dp, end = 7.dp, top = 10.dp, bottom = 28.dp)
                                                .clickable {
                                                    selectedTags.value =
                                                        selectedTags.value.filter { it !in item };
                                                    listTags.value = listTags.value + item;
                                                },
                                            color = DarkGreen
                                        )

                                        //}
                                    }
                                }*/
                                FlowRow(
                                    modifier = Modifier.padding(start = 12.dp, end = 7.dp, top = 10.dp, bottom = 28.dp)
                                ) {
                                    selectedTags.value.forEach { item ->
                                        Text(
                                            text = item,
                                            color = DarkGreen,
                                            modifier = Modifier
                                                .padding(end = 5.dp, bottom = 5.dp)
                                                .clickable {
                                                    selectedTags.value =
                                                        selectedTags.value.filter { it !in item };
                                                    listTags.value = listTags.value + item;
                                                }
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider(thickness = 1.dp, color = Brown.copy(0.5f), modifier = Modifier.padding(start = 33.dp, end = 33.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 18.dp, end = 18.dp)
                        ) {
                            // Поиск
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = LightBrown,
                                    focusedContainerColor = LightBrown,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = DarkGreen,
                                    unfocusedTextColor = DarkGreen
                                ),
                                shape = RoundedCornerShape(0.dp),
                                label = { Text("Тэги", color = Brown) },
                                trailingIcon = {
                                    IconButton(onClick = { expanded = !expanded }) {
                                        Icon(
                                            painter = if (expanded) painterResource(R.drawable.arrow) else painterResource(
                                                R.drawable.arrow_up
                                            ),
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 18.dp, end = 18.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                                    .background(LightBrown)
                            ) {
                                items(listTags.value.filter {
                                    it.contains(
                                        searchQuery,
                                        ignoreCase = true
                                    )
                                }) { tag ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = selectedTags.value.contains(tag),
                                            onCheckedChange = {
                                                if (it) {
                                                    selectedTags.value += tag;
                                                    listTags.value = listTags.value.filter { it !in tag }
                                                }
                                                else
                                                    selectedTags.value = selectedTags.value.filter { it !in tag }

                                            }
                                        )
                                        Text(text = tag, color = Brown, fontSize = 16.sp)
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 18.dp, end = 18.dp, top = 17.dp)
                        ) {
                            TextField(
                                value = status,
                                onValueChange = { newText -> status = newText },
                                shape = RoundedCornerShape(3.dp),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
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
                        HorizontalDivider(thickness = 1.dp, color = Brown.copy(0.5f), modifier = Modifier.padding(start = 33.dp, end = 33.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 18.dp, end = 18.dp)
                        ) {
                            TextField(
                                value = "Статус",
                                onValueChange = { newText -> name = newText },
                                shape = RoundedCornerShape(3.dp),
                                readOnly = true,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
                                    .background(LightBrown),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = LightBrown,
                                    focusedContainerColor = LightBrown,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = Brown,
                                    unfocusedTextColor = Brown
                                )
                            )
                        }

                        HorizontalDivider(thickness = 1.dp, color = Brown, modifier = Modifier.padding(top = 23.dp, bottom = 23.dp, start = 6.dp, end = 6.dp))

                    }
                }
            }
            item {
                Box {
                    LazyColumn(
                        modifier = Modifier
                            .height(135.dp*chapters.value.count())
                        //.fillMaxSize()
                        ,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(chapters.value) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Brown)
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LightBrown
                                )
                                Icon(
                                    painter = painterResource(R.drawable.author),
                                    contentDescription = "",
                                    tint = LightBrown,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .background(LightGreen),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(modifier = Modifier
                .background(LightGreen)
                .height(57.dp)
                .clickable {  }) {
                Text(
                    "Добавить новую главу",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 19.dp, bottom = 19.dp)
                )
            }
        }
    }

/*    Box {
        Box(modifier = Modifier.fillMaxWidth().background(LightGreen)){

        }
    }*/
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateEditWorkData(navHost: NavHostController, viewModel: CreateEditWorkVM, work: String?) {



}