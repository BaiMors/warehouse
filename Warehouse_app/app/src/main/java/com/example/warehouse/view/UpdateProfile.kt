package com.example.warehouse.view

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.warehouse.R
import com.example.warehouse.models.Users
import com.example.warehouse.service.Constants
import com.example.warehouse.service.ContentUriFetcher
import com.example.warehouse.service.UploadOptions
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.view_models.MainViewModel
import com.example.warehouse.view_models.ProfileVM
import io.github.jan.supabase.auth.auth
import io.ktor.http.ContentDisposition.Companion.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.UUID

@Composable
fun UpdateProfile(navHost: NavHostController, viewModel: ProfileVM, viewModel1: MainViewModel) {
    val isUserLoaded by viewModel.isUserLoaded.collectAsState()
    val isDataLoaded by viewModel1.isDataLoaded.collectAsState()
    println("isUserLoaded::::: "+isUserLoaded)
    println("isDataLoaded::::: "+isDataLoaded)
    var selectedImage by remember {
        mutableStateOf<String?>(null)
    }

    Box(
        Modifier
            .fillMaxSize()
            .zIndex(1f)
            .background(DarkGreen)
            .padding(bottom = 20.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.back),
            contentDescription = "",
            tint = LightBrown,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 21.dp, end = 21.dp, bottom = 25.dp, top = 45.dp)
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
            println("зашли в отрисовку isUserLoaded::::: "+isUserLoaded)
            println("зашли в отрисовку isDataLoaded::::: "+isDataLoaded)
            UpdateProfileData(navHost, viewModel, viewModel1, selectedImage)
        }
    }
}

@Composable
fun UpdateProfileData(navHost: NavHostController, viewModel: ProfileVM, viewModel1: MainViewModel, selectedImage: String?) {
    val ud = viewModel.userList.collectAsState()
    println("ща найду userData")
    val userData = ud.value.find { it.id == Constants.supabase.auth.currentUserOrNull()!!.id }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val desc = remember { mutableStateOf("") }
    val upsertResult by viewModel.upsertResult.collectAsState()
    val ctx = LocalContext.current

/*    email.value = Constants.supabase.auth.currentUserOrNull()!!.email!!
    name.value = userData!!.name
    username.value = userData!!.username
    desc.value == userData!!.description*/


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            //.background(DarkGreen)
            .padding(top = 80.dp)
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (userData!!.image == null)
            //val im = MainViewModel.PrefsHelper.getSharedPreferences().getString("im", null)
            //if (im == null)
            {
                //println("im равно нуллу")
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
                //PhotoSelectorView(selectedImage)
            }
            else
            {
                AsyncImage(
                    model = userData.image,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(128.dp)
                        .width(128.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                //PhotoSelectorView()
                //val buf = MainViewModel.PrefsHelper.getSharedPreferences().getString("im", null)
                //ImageLayoutView(buf!!)
            }
        }

        //
        Column(
            modifier = Modifier.padding(bottom = 20.dp).align(Alignment.CenterHorizontally),
        ) {
            Text(
                "Имя",
                color = LightBrown,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = name.value,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> name.value = newText },
                maxLines = 1,
                placeholder = {
                    Text(
                        text = userData!!.name,
                        color = Brown.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Bold
                    )
                },
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .width(300.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightBrown,
                    focusedContainerColor = LightBrown,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen
                )
            )

            Text(
                "Имя пользователя",
                color = LightBrown,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = username.value,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> username.value = newText },
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "@"+userData!!.username,
                        color = Brown.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Bold
                    )
                },
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .width(300.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightBrown,
                    focusedContainerColor = LightBrown,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen
                )
            )

            Text(
                "Описание",
                color = LightBrown,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = desc.value,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> desc.value = newText },
                maxLines = Int.MAX_VALUE,
                placeholder = {
                    Text(
                        text = userData!!.description!!,
                        color = Brown.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Bold
                    )
                },
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .height(126.dp)
                    .width(300.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightBrown,
                    focusedContainerColor = LightBrown,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen
                )
            )

            Canvas(
                Modifier
                    .width(128.dp)
                    .padding(top = 23.dp, start = 21.dp, end = 21.dp, bottom = 23.dp)
                    .height(1.dp)
                    .background(Color.Transparent)
                    .align(Alignment.CenterHorizontally)) {
                drawLine(
                    color = LightBrown.copy(0.5f),
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = size.height
                )
            }

            Text(
                "Логин",
                color = LightBrown,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = email.value,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> email.value = newText },
                maxLines = 1,
                placeholder = {
                    Text(
                        text = Constants.supabase.auth.currentUserOrNull()!!.email!!,
                        color = Brown.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Bold
                    )
                },
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .width(300.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightBrown,
                    focusedContainerColor = LightBrown,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen
                )
            )

            var passwordVisibility: Boolean by remember { mutableStateOf(false) }
            Text(
                "Пароль",
                color = LightBrown,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = password.value,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> password.value = newText },
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "123456",
                        color = Brown.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Bold
                    )
                },
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .width(300.dp)
                    .height(55.dp),
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


        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier
                    .width(270.dp)
                    .height(70.dp)
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(Brown),
                shape = RoundedCornerShape(3.dp),
                onClick = {
                    println("name.value:::::::::"+name.value.isEmpty())
                    viewModel.updateUserProfile(
                        ctx,
                        if (name.value.isEmpty()) userData!!.name else name.value,
                        //"Bai",
                        if (username.value.isEmpty()) userData!!.username else username.value,
                        //"y_k_e_s",
                        if (email.value.isEmpty()) Constants.supabase.auth.currentUserOrNull()!!.email else email.value,
                        //"ardaismine@gmail.com",
                        password.value,
                        if (desc.value.isEmpty()) userData!!.description else desc.value,
                        //"Standing next to you~",
                        userData!!.title,
                        //"https://i.pinimg.com/736x/ee/0b/05/ee0b05853406d693856978f7b4cbf1b4.jpg"
                        userData!!.image
                    )
                    navHost.navigate("Profile")
                }) {
                Text(
                    "Сохранить изменения",
                    fontSize = 20.sp,
                    color = LightBrown,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    when (upsertResult) {
        is ProfileVM.Result.Success -> {
            //navHost.navigate("Profile")
            Toast.makeText(ctx, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
            //navHost.navigate("Profile")
        }
        is ProfileVM.Result.Error -> {
            Toast.makeText(ctx, "Error: ${(upsertResult as ProfileVM.Result.Error).message}", Toast.LENGTH_SHORT).show()
        }
        null -> {
            // Ожидание результата, ничего не делаем
        }
    }
}




/*@Composable
fun PhotoSelectorView(selectedImage: String?) {
    //val ctx = LocalContext.current
    //var uploadedImageUrl by remember { mutableStateOf<String?>(null) } // Хранит URL загруженного изображения
    var buf = selectedImage
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> buf = uri.toString() }
    )
    println("оп получили фоточку "+buf)
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
        Text("uri? of image i think"+buf)
        ImageLayoutView(selectedImage = buf)
        val editor = MainViewModel.PrefsHelper.getSharedPreferences().edit()
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        val ctx = LocalContext.current
        ctx.contentResolver.takePersistableUriPermission(buf!!.toUri(), flag)
        editor.putString("im", buf.toString())
        editor.apply()
    }
}

@Composable
fun ImageLayoutView(selectedImage: String?) {
    Row {
        Image(
            painter = rememberAsyncImagePainter(model = selectedImage),
            contentDescription = null,
            modifier = Modifier.width(300.dp).height(300.dp).padding(top = 200.dp),
            contentScale = ContentScale.Crop
        )
    }
}*/

/*fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.readBytes()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun byteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap? {
    return try {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}*/

/**
 * Загружает массив байтов в указанный bucket на Supabase.
 * @param byteArray Массив байтов, представляющий изображение.
 * @param bucketName Имя bucket, в который загружается файл (например, "profile_pictures").
 * @param supabaseClient Инициализированный клиент Supabase.
 * @return Имя загруженного файла (или null, если произошла ошибка).
 *//*
suspend fun uploadByteArrayToSupabase(
    byteArray: ByteArray,
    bucketName: String,
    supabaseClient: io.github.jan.supabase.SupabaseClient
): String? {
    return withContext(Dispatchers.IO) { // Работа с файлами должна выполняться в IO-потоке
        try {
            // Генерируем уникальное имя файла с расширением .jpg
            val fileName = "${UUID.randomUUID()}.jpg"

            // Получаем доступ к bucket
            val storage = supabaseClient.storage
            val bucket = storage.from(bucketName)

            // Загружаем файл в bucket
            bucket.upload(fileName, byteArray)

            // Возвращаем имя файла для последующего использования
            fileName
        } catch (e: Exception) {
            // Обработка ошибок, если загрузка не удалась
            e.printStackTrace()
            null
        }
    }
}

*//**
 * Получает публичный URL для файла в Supabase Storage.
 * @param bucketName Имя bucket, в котором хранится файл.
 * @param fileName Имя файла, для которого нужно получить ссылку.
 * @param supabaseClient Инициализированный клиент Supabase.
 * @return Публичный URL файла.
 *//*
fun getPublicImageUrl(
    bucketName: String,
    fileName: String,
    supabaseClient: io.github.jan.supabase.SupabaseClient
): String {
    // Получаем доступ к bucket
    val storage = supabaseClient.storage
    val bucket = storage.from(bucketName)

    // Возвращаем публичный URL файла
    return bucket.publicUrl(fileName)
}

*//**
 * Конвертирует URI изображения в массив байтов.
 * @param context Контекст приложения для доступа к ContentResolver.
 * @param uri URI изображения.
 * @return Массив байтов (или null, если произошла ошибка).
 *//*
fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        // Открываем InputStream для доступа к содержимому URI
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { it.readBytes() } // Читаем все байты из потока
    } catch (e: Exception) {
        // Обработка ошибок при чтении данных
        e.printStackTrace()
        null
    }
}*/

/**
 * Компонент для выбора изображения и загрузки его в Supabase Storage.
 * @param viewModel ViewModel для управления состоянием.
 * @param supabaseClient Инициализированный клиент Supabase.
 */
//@Composable
/*fun UploadProfilePhotoWithByteArray(viewModel: ProfileViewModel, supabaseClient: io.supabase.SupabaseClient) {
    val context = LocalContext.current // Получаем контекст приложения
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) } // Хранит выбранный URI
    var uploadedImageUrl by remember { mutableStateOf<String?>(null) } // Хранит URL загруженного изображения

    // Лаунчер для открытия медиапикера
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri // Сохраняем URI выбранного изображения
            uri?.let {
                // Конвертируем URI в массив байтов
                val byteArray = uriToByteArray(context, it)
                byteArray?.let { bytes ->
                    // Загружаем изображение в Supabase Storage
                    viewModel.scope.launch {
                        val fileName = uploadByteArrayToSupabase(
                            byteArray = bytes,
                            bucketName = "profile_pictures", // Имя bucket
                            supabaseClient = supabaseClient
                        )
                        if (fileName != null) {
                            // Получаем публичный URL загруженного изображения
                            uploadedImageUrl = getPublicImageUrl("profile_pictures", fileName, supabaseClient)
                        }
                    }
                }
            }
        }
    )

    // UI компоненты
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Кнопка для запуска медиапикера
        Button(onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
            Text("Выбрать фото")
        }

        // Отображение загруженного изображения
        uploadedImageUrl?.let {
            AsyncImage(
                model = it, // Указываем URL изображения
                contentDescription = "Профильное фото",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape), // Обрезка в форме круга
                contentScale = ContentScale.Crop
            )
        }
    }
}*/


