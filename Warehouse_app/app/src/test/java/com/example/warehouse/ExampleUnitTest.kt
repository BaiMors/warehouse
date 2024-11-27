package com.example.warehouse

import androidx.lifecycle.viewModelScope
import com.example.warehouse.service.Constants
import com.example.warehouse.view_models.AvtorizationVM
import com.example.warehouse.view_models.MainViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import kotlin.time.Duration

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModel1: AvtorizationVM

    /**
     * Тест на правильность установки состояния Success при успешном входе в систему
     * */
    @Test
    fun signInStateIsSuccessAfterSuccessfulSignIn() {
        val viewModel1 = AvtorizationVM()

        // Устанавливаем пользовательский макет
        viewModel1._authResult.value = AvtorizationVM.Result.Success(Unit)

        // Проверяем, что состояние signInState было правильно установлено
        assertEquals(viewModel1._authResult.value, AvtorizationVM.Result.Success(Unit))
    }

    /**
     * Тест на правильность установки состояния Success при успешной регистрации
     * */
    @Test
    fun signUpStateIsSuccessAfterSuccessfulSignUp() {
        val viewModel1 = AvtorizationVM()

        viewModel1._regResult.value = AvtorizationVM.Result.Success(Unit)

        assertEquals(viewModel1._regResult.value, AvtorizationVM.Result.Success(Unit))
    }

    /**
     * Тест на проверку правильности установки состояния Error после неудачной попытки входа в систему
     * */
    @Test
    fun signInStateIsErrorAfterFailedSignIn() {
        val viewModel1 = AvtorizationVM()
        val mockError = Exception("Failed to sign in")

        viewModel1._authResult.value = AvtorizationVM.Result.Error(mockError.message.toString())

        assertEquals(viewModel1._authResult.value, AvtorizationVM.Result.Error(mockError.message.toString()))
    }

    /**
     * Тест на проверку правильности установки состояния Error после неудачной попытки регистрации
     * */
    @Test
    fun signUpStateIsErrorAfterFailedSignUp() {
        val viewModel1 = AvtorizationVM()
        val mockError = Exception("Failed to sign in")

        viewModel1._authResult.value = AvtorizationVM.Result.Error(mockError.message.toString())

        assertEquals(viewModel1._authResult.value, AvtorizationVM.Result.Error(mockError.message.toString()))
    }

    @Test
    fun gotAllWorks() {
        val viewModel = MainViewModel()
        var countWorks = viewModel.worksList.value.count()

        viewModel.viewModelScope.launch {
            val response = Constants.supabase.postgrest.rpc("get_num_works").decodeList<Int>()
            countWorks = Constants.supabase.postgrest.rpc("get_num_works").decodeList<Int>()[0]
            delay(5000)
        }
        assertEquals(viewModel.worksList.value.count(), countWorks)
    }
}