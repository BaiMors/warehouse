package com.example.warehouse

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.warehouse.service.Constants
import com.example.warehouse.view.Avtorization
import com.example.warehouse.view.MainPage
import com.example.warehouse.view_models.AvtorizationVM
import com.example.warehouse.view_models.AvtorizationVM.Result.Error
import com.example.warehouse.view_models.AvtorizationVM.Result.Success
import com.example.warehouse.view_models.MainPageViewModel
import com.example.warehouse.view_models.MainViewModel
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.warehouse", appContext.packageName)
    }

    /**
     * Проверка отображения экрана авторизации
     * */
    @Test
    fun SignUpscreenisdisplayed() {
        val ctx = composeTestRule.activity.applicationContext
        val sp = ctx.getSharedPreferences("user_email", Context.MODE_PRIVATE)
        sp.edit().putString("user_email", "test@gmail.com").apply()

        composeTestRule.setContent {
            MainPage(rememberNavController(), MainPageViewModel(), MainViewModel())
        }
        // Проверяем, что экран регистрации отображается
        composeTestRule.onNodeWithText("SigUp").assertIsDisplayed()
    }
/*    *//**
     * Проверка перехода на экран входа в систему при нажатии на кнопку SigIn
     * *//*
    @Test
    fun NavigatetoSignInscreenafterclickingSigInbutton() {
        composeTestRule.setContent {
            auth(rememberNavController())
        }

        composeTestRule.onNodeWithText("SigIn").performClick()

        // Проверяем, что пользователь был перенаправлен на HomeScreen (Тест провалится, потому что пользователя не залогинился)
        composeTestRule.onNodeWithText("HomeScreen").assertIsDisplayed()
    }*/
}