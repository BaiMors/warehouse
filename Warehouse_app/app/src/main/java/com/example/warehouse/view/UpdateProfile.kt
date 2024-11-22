package com.example.warehouse.view

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.warehouse.R
import com.example.warehouse.models.Users
import com.example.warehouse.service.Constants
import com.example.warehouse.service.ContentUriFetcher
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.view_models.MainViewModel
import com.example.warehouse.view_models.ProfileVM
import io.github.jan.supabase.auth.auth

@Composable
fun UpdateProfile(navHost: NavHostController, viewModel: ProfileVM, viewModel1: MainViewModel) {
    val isUserLoaded by viewModel.isUserLoaded.collectAsState()
    val isDataLoaded by viewModel1.isDataLoaded.collectAsState()
    println("isUserLoaded::::: "+isUserLoaded)
    println("isDataLoaded::::: "+isDataLoaded)

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
                .padding(start = 21.dp, end = 21.dp, bottom = 25.dp, top = 45.dp).zIndex(1f)
                .clickable { navHost.navigateUp() }
        )
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
            println("зашли в отрисовку UpdateProfileData::::: "+isUserLoaded)
            println("зашли в отрисовку UpdateProfileData::::: "+isDataLoaded)
            UpdateProfileData(navHost, viewModel, viewModel1)
        }
    }
}

@Composable
fun UpdateProfileData(navHost: NavHostController, viewModel: ProfileVM, viewModel1: MainViewModel) {
    val ud = viewModel.userList.collectAsState()
    println("ща найду userData")
    val userData = ud.value.find { it.id == Constants.supabase.auth.currentUserOrNull()!!.id }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkGreen)
            .padding(top = 80.dp)
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    add(ContentUriFetcher.Factory(LocalContext.current))
                }
                .build()
            if (userData!!.image == null)
            {
                Canvas(
                    Modifier
                        .size(128.dp)
                        .background(Color.Transparent)
                        .align(Alignment.Center)) {
                    drawCircle(
                        color = LightBrown,
                        center = center,
                    )
                }
                PhotoSelectorView()
            }
            else
            {
                AsyncImage(
                    //model = userData.image,
                    model = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000397",
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(128.dp)
                        .width(128.dp).clip(RoundedCornerShape(100)),
                    contentScale = ContentScale.Crop,
                    imageLoader = imageLoader
                )
                PhotoSelectorView()
            }
        }
    }

}

@Composable
fun PhotoSelectorView() {
    var selectedImage by remember {
        mutableStateOf<String?>("")
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImage = uri.toString() }
    )
    fun launchPhotoPicker(){
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(modifier = Modifier.align(Alignment.Center), onClick = {
            launchPhotoPicker()
        }) {
            Text("Select a photo")
        }
        Text("uri? of image i think"+selectedImage)
        ImageLayoutView(selectedImage = selectedImage)
    }
}

@Composable
fun ImageLayoutView(selectedImage: String?) {
    Row {
        AsyncImage(
            model = selectedImage,
            contentDescription = null,
            modifier = Modifier.width(300.dp).height(300.dp).padding(top = 200.dp),
            contentScale = ContentScale.Crop
        )
    }
}
