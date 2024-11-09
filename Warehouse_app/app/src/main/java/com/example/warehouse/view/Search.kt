package com.example.warehouse.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.view_models.MainPageViewModel
import com.example.warehouse.view_models.SearchViewModel

@Composable
fun Search(navHost: NavHostController, viewModel: SearchViewModel){
    val searchStr = remember { mutableStateOf("") }
    TextField(
        value = searchStr.value,
        textStyle = TextStyle(fontSize = 15.sp),
        onValueChange = { newText -> searchStr.value = newText },
        maxLines = 1,
        modifier = Modifier
            .padding(bottom = 25.dp)
            .width(300.dp)
            .height(55.dp),
    )
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = {
            viewModel.filterWorks(searchStr.value)
        }) {
        Text(
            "search",
            fontSize = 14.sp,
            color = LightBrown,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }


}