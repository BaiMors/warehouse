package com.example.warehouse.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.warehouse.R
import com.example.warehouse.ui.theme.Brown
import com.example.warehouse.ui.theme.DarkGreen
import com.example.warehouse.ui.theme.LightBrown
import com.example.warehouse.view_models.AvtorizationVM
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController

//navHost: NavHostController, viewModel: AvtorizationVM.Companion
//@Preview
@Composable
fun Avtorization(navHost: NavHostController, viewModel: AvtorizationVM) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val authResult by viewModel.AuthResult.collectAsState()
    //val regResult by viewModel.create().regResult.collectAsState()
    val ctx = LocalContext.current
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current




    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkGreen)
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Icon(
            painter = painterResource(R.drawable.home_avt),
            contentDescription = "",
            tint = LightBrown,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Text(
            "Авторизация",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = LightBrown,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp)
        )

        Column(
            modifier = Modifier.padding(bottom = 20.dp),
        ) {

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
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .padding(bottom = 25.dp)
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


            //var passwordVisibility: Boolean by remember { mutableStateOf(false) }
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
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
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
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier
                    .width(260.dp)
                    .height(70.dp)
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(Brown),
                shape = RoundedCornerShape(3.dp),
                onClick = {
                    viewModel.onSignInEmailPassword(email.value, password.value)
                }) {
                Text(
                    "Войти",
                    fontSize = 20.sp,
                    color = LightBrown,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                onClick = {
                    navHost.navigate("Registration")
                }) {
                Text(
                    "С нами в первый раз? Зарегистрируйтесь!",
                    fontSize = 14.sp,
                    color = LightBrown,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    // Обработка результата авторизации
    when (authResult) {
        is AvtorizationVM.Result.Success -> {
            // Если авторизация успешна, навигация на другой экран
            navHost.navigate("MainPage")
        }
        is AvtorizationVM.Result.Error -> {
            // Если произошла ошибка, показываем сообщение об ошибке
            Toast.makeText(ctx, "Error: ${(authResult as AvtorizationVM.Result.Error).message}", Toast.LENGTH_SHORT).show()
        }
        null -> {
            // Ожидание результата, ничего не делаем
        }
    }
}